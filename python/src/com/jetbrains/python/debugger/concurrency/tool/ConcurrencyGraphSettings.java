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
package com.jetbrains.python.debugger.concurrency.tool;


import com.intellij.ui.JBColor;

import java.awt.*;

public class ConcurrencyGraphSettings {
  public static int CELL_WIDTH = 1;
  public static int CELL_HEIGHT = 14;
  public static int INTERVAL = 6;
  public static int STROKE_BASIC = 2;
  public static int TABLE_ROW_HEIGHT = 20;
  public static int TIME_CURSOR_WIDTH = 1;

  public static int RULER_STROKE_WIDTH = 1;
  public static Color RULER_COLOR = JBColor.BLACK;
  public static int RULER_UNIT_MARK = 10;
  public static int RULER_SUBUNIT_MARK = 5;
  public static int RULER_SUBUNITS_PER_UNIT = 10;

  public static Color BASIC_COLOR = new JBColor(new Color(152, 251, 152), new Color(152, 251, 152));
  public static Color LOCK_WAIT_COLOR = new JBColor(new Color(255, 179, 3), new Color(255, 179, 3));
  public static Color LOCK_WAIT_SELECTED_COLOR = new JBColor(new Color(255, 240, 0), new Color(255, 240, 0));
  public static Color LOCK_OWNING_COLOR = new JBColor(new Color(135, 206, 250), new Color(135, 206, 250));
  public static Color LOCK_OWNING_SELECTED_COLOR = new JBColor(new Color(0, 100, 255), new Color(0, 100, 255));
  public static Color TIME_CURSOR_COLOR = JBColor.RED;
  public static Color DEADLOCK_COLOR = JBColor.RED;
}
