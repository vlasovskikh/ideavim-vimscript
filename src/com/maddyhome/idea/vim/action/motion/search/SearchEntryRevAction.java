package com.maddyhome.idea.vim.action.motion.search;

/*
 * IdeaVim - A Vim emulator plugin for IntelliJ Idea
 * Copyright (C) 2003-2006 Rick Maddy
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
import com.maddyhome.idea.vim.action.motion.MotionEditorAction;
import com.maddyhome.idea.vim.command.Argument;
import com.maddyhome.idea.vim.command.Command;
import com.maddyhome.idea.vim.group.CommandGroups;
import com.maddyhome.idea.vim.handler.motion.MotionEditorActionHandler;

/**
 *
 */
public class SearchEntryRevAction extends MotionEditorAction {
  public SearchEntryRevAction() {
    super(new Handler());
  }

  private static class Handler extends MotionEditorActionHandler {
    public int getOffset(Editor editor, DataContext context, int count, int rawCount, Argument argument) {
      return CommandGroups.getInstance().getSearch().search(editor, context, argument.getString(),
                                                            count, Command.FLAG_SEARCH_REV, false);
    }
  }
}
