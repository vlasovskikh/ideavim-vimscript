package com.maddyhome.idea.vim.lang.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiFile;
import com.maddyhome.idea.vim.file.VimScriptFileType;
import org.jetbrains.annotations.NotNull;

/**
 * <p>Date: 09.11.11</p>
 * <p></p>
 *
 * @author Ksenia V. Mamich
 * @version 1.0
 */
public class VimScriptPsiFile extends PsiFileBase implements PsiFile {
  public VimScriptPsiFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, VimScriptFileType.VIM_SCRIPT_LANGUAGE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return VimScriptFileType.VIM_SCRIPT_FILE_TYPE;
  }
}
