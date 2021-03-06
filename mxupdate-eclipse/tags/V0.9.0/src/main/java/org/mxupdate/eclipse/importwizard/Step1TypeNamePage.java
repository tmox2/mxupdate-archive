/*
 * Copyright 2008-2010 The MxUpdate Team
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

package org.mxupdate.eclipse.importwizard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.mxupdate.eclipse.Activator;
import org.mxupdate.eclipse.Messages;
import org.mxupdate.eclipse.adapter.ITypeDefNode;
import org.mxupdate.eclipse.adapter.ITypeDefRoot;

/**
 * Page to select the types and define the name wild card used to query within
 * the {@link ImportWizard import wizard}.
 *
 * @author The MxUpdate Team
 * @version $Id$
 */
public class Step1TypeNamePage
    extends WizardPage
{
    /**
     * Text field for the name match string.
     */
    private Text nameField;

    /**
     * Tree with all possible type definitions.
     */
    private Tree tree;

    /**
     * Map holding already created images for this wizard page. The images
     * are used within the tree table showing the type definition tree.
     *
     * @see #dispose()
     * @see #append(ITypeDefNode, TreeItem)
     */
    private final List<Image> images = new ArrayList<Image>();

    /**
     * Initializes step 1 page of the import wizard.
     */
    public Step1TypeNamePage()
    {
        super("Step1");
        this.setTitle(Messages.getString("ImportWizard.Wizard.Step1.Title")); //$NON-NLS-1$
        this.setDescription(Messages.getString("ImportWizard.Wizard.Step1.Description")); //$NON-NLS-1$
    }

    /**
     * All {@link #images} must be disposed.
     *
     * @see #images
     */
    @Override()
    public void dispose()
    {
        super.dispose();
        for (final Image image : this.images)  {
            image.dispose();
        }
        this.images.clear();
    }

    /**
     * Creates the control for the first step of the import wizard.
     *
     * @param _parent   parent composite
     */
    public void createControl(final Composite _parent)
    {
        final ITypeDefRoot typeDefRoot = Activator.getDefault().getAdapter().getTypeDefRoot();

        this.initializeDialogUnits(_parent);

        final Composite containerGroup = new Composite(_parent, 0);
        final GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        containerGroup.setLayout(layout);
        containerGroup.setLayoutData(new GridData(768));
        containerGroup.setFont(_parent.getFont());

        this.nameField = new Text(containerGroup, 2052);
        final GridData data = new GridData(768);
        data.widthHint = 250;
        this.nameField.setLayoutData(data);
        this.nameField.setFont(_parent.getFont());
        this.nameField.addKeyListener(new KeyListener()
        {
            public void keyPressed(final KeyEvent _event)
            {
            }
            public void keyReleased(final KeyEvent _event)
            {
                Step1TypeNamePage.this.validate();
            }
        });

        this.tree = new Tree(containerGroup, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        this.tree.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 1, 1));
        for (final ITypeDefNode typeDefNode : typeDefRoot.getSubTypeDef())  {
            this.append(typeDefNode, new TreeItem(this.tree, SWT.NONE));
        }
        this.tree.addSelectionListener(new SelectionListener()
        {
            public void widgetDefaultSelected(final SelectionEvent _event)
            {
            }
            public void widgetSelected(final SelectionEvent _event)
            {
                Step1TypeNamePage.this.validate();
            }
        });

        this.setControl(containerGroup);
        this.validate();
    }

    /**
     * Appends to the tree <code>_item</code> which are defined as
     * <code>_node</code> all sub nodes of the tree.
     *
     * @param _node     current type definition node
     * @param _item     current tree item
     */
    protected void append(final ITypeDefNode _node,
                          final TreeItem _item)
    {
        _item.setText(_node.getLabel());
        _item.setData(_node);
        // if one type definition is defined show related image
        if (_node.getTypeDefs().size() == 1)  {
            final String typeDef = _node.getTypeDefs().iterator().next();
            final ImageDescriptor imageDescr = Activator.getDefault().getAdapter().getImageDescriptor(typeDef);
            if (imageDescr != null)  {
                final Image image = imageDescr.createImage();
                this.images.add(image);
                _item.setImage(image);
            }
        }
        for (final ITypeDefNode subNode : _node.getSubTypeDef())  {
            this.append(subNode, new TreeItem(_item, SWT.NONE));
        }
    }

    /**
     * Validates if one minimum one item of the {@link #tree} is selected and a
     * text is entered in {@link #nameField}. If not an error message is shown.
     * Otherwise the page is completed.
     *
     * @see #tree
     * @see #nameField
     */
    protected void validate()
    {
        if ("".equals(this.nameField.getText()))  { //$NON-NLS-1$
            this.setErrorMessage(Messages.getString("ImportWizard.Wizard.Step1.NameError")); //$NON-NLS-1$
            Step1TypeNamePage.this.setPageComplete(false);
        } else if (Step1TypeNamePage.this.tree.getSelectionCount() == 0)  {
            this.setErrorMessage(Messages.getString("ImportWizard.Wizard.Step1.TypeDefError")); //$NON-NLS-1$
            Step1TypeNamePage.this.setPageComplete(false);
        } else  {
            this.setErrorMessage(null);
            Step1TypeNamePage.this.setPageComplete(true);
        }
    }

    /**
     * Checks that one minimum one item of the {@link #tree} is selected and a
     * text is entered in {@link #nameField}.
     *
     * @return <i>true</i> if at one item is selected and a text is entered
     * @see #tree
     * @see #nameField
     */
    @Override()
    public boolean canFlipToNextPage()
    {
        return (Step1TypeNamePage.this.tree.getSelectionCount() > 0) && !"".equals(this.nameField.getText());
    }

    /**
     * If this page is the current page of the wizard, the page (meaning the
     * wizard) is not complete (because not the last step of the wizard).
     *
     * @return always <i>false</i> if this page is the current page of the
     *         wizard; otherwise <i>false</i>
     */
    @Override()
    public boolean isPageComplete()
    {
        return !this.isCurrentPage();
    }


    /**
     * Returns the match text from the {@link #nameField name field}.
     *
     * @return match text from the name field
     * @see #nameField
     */
    public String getMatch()
    {
        return this.nameField.getText();
    }

    /**
     * Returns the set of select type definitions in the {@link #tree}.
     *
     * @return set of type definitions
     */
    public Set<String> getTypeDefs()
    {
        final Set<String> ret = new HashSet<String>();
        for (final TreeItem item : this.tree.getSelection())  {
            ret.addAll(((ITypeDefNode) item.getData()).getTypeDefs());
        }
        return ret;
    }
}
