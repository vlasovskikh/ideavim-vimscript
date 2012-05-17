package com.maddyhome.idea.vim.ex.handler;

/*
 * IdeaVim - A Vim emulator plugin for IntelliJ Idea
 * Copyright (C) 2003-2005 Rick Maddy
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.maddyhome.idea.vim.command.Command;
import com.maddyhome.idea.vim.common.TextRange;
import com.maddyhome.idea.vim.ex.*;
import com.maddyhome.idea.vim.group.CommandGroups;
import com.maddyhome.idea.vim.helper.EditorHelper;
import com.maddyhome.idea.vim.helper.MessageHelper;
import com.maddyhome.idea.vim.helper.Msg;

/**
 *
 */
public class MoveTextHandler extends CommandHandler {
  public MoveTextHandler() {
    super("m", "ove", RANGE_OPTIONAL | ARGUMENT_REQUIRED | WRITABLE);
  }

  public boolean execute(Editor editor, DataContext context, ExCommand cmd) throws ExException {
    TextRange range = cmd.getTextRange(editor, context, false);
    LineRange lr = cmd.getLineRange(editor, context, false);
    int adj = lr.getEndLine() - lr.getStartLine() + 1;

    ParseResult pr = CommandParser.getInstance().parse(cmd.getArgument());
    int line = pr.getRanges().getFirstLine(editor, context);

    if (line >= lr.getEndLine()) {
      line -= adj;
    }
    else if (line >= lr.getStartLine()) {
      throw new InvalidRangeException(MessageHelper.message(Msg.e_backrange));
    }

    String text = EditorHelper.getText(editor, range.getStartOffset(), range.getEndOffset());

    editor.getDocument().deleteString(range.getStartOffset(), range.getEndOffset());

    int offset = CommandGroups.getInstance().getMotion().moveCaretToLineStart(editor, line + 1);
    CommandGroups.getInstance().getCopy().putText(editor, context, offset, text, Command.FLAG_MOT_LINEWISE, 1, true,
                                                  false, 0);

    return true;
  }
}
