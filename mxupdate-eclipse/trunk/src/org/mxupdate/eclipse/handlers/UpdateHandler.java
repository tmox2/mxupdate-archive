/*
 * Copyright 2008-2009 The MxUpdate Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Revision:        $Rev$
 * Last Changed:    $Date$
 * Last Changed By: $Author$
 */

package org.mxupdate.eclipse.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;
import org.mxupdate.eclipse.Activator;

/**
 * Eclipse Handler called from the update command used to update selected TCL
 * update file.
 *
 * @author Tim Moxter
 * @version $Id$
 */
public class UpdateHandler
        extends AbstractHandler
{
    /**
     * the command has been executed, so extract extract the needed information
     * from the application context.
     *
     * @param _event  execution event
     * @return always <code>null</code>
     * @see Activator#update(String)
     */
    public Object execute(final ExecutionEvent _event)
    {
        final ISelection selection = HandlerUtil.getCurrentSelection(_event);

        // selection from the navigator? (popup)
        if (selection instanceof TreeSelection)  {
            final TreeSelection treeSel = (TreeSelection) selection;
            for (final Object obj : treeSel.toList())  {
                final IFile file = (IFile) obj;
                Activator.getDefault().update(file.getLocation().toString());
            }
        // started within editor or as toolbar command
        } else  {
            final IEditorPart activeEditor = HandlerUtil.getActiveEditor(_event);
            if (activeEditor != null)  {
                final IEditorInput input = activeEditor.getEditorInput();
                if (input instanceof IFileEditorInput)  {
                    final IFile file = ((IFileEditorInput) input).getFile();
                    Activator.getDefault().update(file.getLocation().toString());
                }
            }
        }
        return null;
    }
}