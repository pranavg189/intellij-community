/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.refactoring.typeMigration.rules.guava;

import com.intellij.codeInspection.AnonymousCanBeLambdaInspection;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Comparing;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.InheritanceUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiTypesUtil;
import com.intellij.refactoring.typeMigration.TypeConversionDescriptor;
import com.intellij.refactoring.typeMigration.TypeEvaluator;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

/**
 * @author Dmitry Batkovich
 */
public class GuavaTypeConversionDescriptor extends TypeConversionDescriptor {
  private static final Logger LOG = Logger.getInstance(GuavaTypeConversionDescriptor.class);
  private final String myReplaceByStringSource;
  private boolean myConvertParameterAsLambda = true;

  GuavaTypeConversionDescriptor(@NonNls String stringToReplace, @NonNls String replaceByString) {
    super(stringToReplace, replaceByString);
    myReplaceByStringSource = replaceByString;
  }

  public GuavaTypeConversionDescriptor setConvertParameterAsLambda(boolean convertParameterAsLambda) {
    myConvertParameterAsLambda = convertParameterAsLambda;
    return this;
  }

  @Override
  public PsiExpression replace(PsiExpression expression, TypeEvaluator evaluator) throws IncorrectOperationException {
    LOG.assertTrue(expression instanceof PsiMethodCallExpression);
    PsiMethodCallExpression methodCall = (PsiMethodCallExpression)expression;
    setReplaceByString(myReplaceByStringSource + (isIterable(methodCall) ? ".collect(java.util.stream.Collectors.toList())" : ""));
    if (myConvertParameterAsLambda) {
      final PsiExpression[] arguments = methodCall.getArgumentList().getExpressions();
      if (arguments.length == 1) {
        GuavaConversionUtil.adjust(arguments[0], false, null, evaluator);
      }
    }
    return super.replace(expression, evaluator);
  }

  public static boolean isIterable(PsiMethodCallExpression expression) {
    final PsiElement parent = expression.getParent();
    if (parent instanceof PsiLocalVariable) {
      return isIterable(((PsiLocalVariable)parent).getType());
    }
    else if (parent instanceof PsiReturnStatement) {
      final PsiElement methodOrLambda = PsiTreeUtil.getParentOfType(parent, PsiMethod.class, PsiLambdaExpression.class);
      PsiType methodReturnType = null;
      if (methodOrLambda instanceof PsiMethod) {
        methodReturnType = ((PsiMethod)methodOrLambda).getReturnType();
      }
      else if (methodOrLambda instanceof PsiLambdaExpression) {
        methodReturnType = LambdaUtil.getFunctionalInterfaceReturnType((PsiFunctionalExpression)methodOrLambda);
      }
      return isIterable(methodReturnType);
    }
    return false;
  }

  private static boolean isIterable(@Nullable PsiType type) {
    PsiClass aClass;
    return (aClass = PsiTypesUtil.getPsiClass(type)) != null && CommonClassNames.JAVA_LANG_ITERABLE.equals(aClass.getQualifiedName());
  }
}
