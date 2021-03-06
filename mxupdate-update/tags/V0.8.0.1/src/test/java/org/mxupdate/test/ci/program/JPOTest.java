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

package org.mxupdate.test.ci.program;

import org.mxupdate.test.AbstractDataExportUpdate;
import org.mxupdate.test.AbstractTest;
import org.mxupdate.test.ExportParser;
import org.mxupdate.test.data.DataCollection;
import org.mxupdate.test.data.program.JPOProgramData;
import org.mxupdate.test.data.user.PersonAdminData;
import org.mxupdate.test.data.util.PropertyDef;
import org.mxupdate.test.util.IssueLink;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test cases for the export and update of JPO programs.
 *
 * @author The MxUpdate Team
 * @version $Id$
 */
public class JPOTest
    extends AbstractDataExportUpdate<JPOProgramData>
{
    /**
     * Creates for given <code>_name</code> a new JPO program instance.
     *
     * @param _name     name of the MQL program instance
     * @return JPO program instance
     */
    @Override()
    protected JPOProgramData createNewData(final String _name)
    {
        return new JPOProgramData(this, _name);
    }

    /**
     * Data provider for test JPOs.
     *
     * @return object array with all test JPOs
     */
    @IssueLink("26")
    @DataProvider(name = "jpos")
    public Object[][] getJPOs()
    {
        final DataCollection data1 = new DataCollection(this);
        data1.getJPOProgram("Test1")
             .setCode("public class " + data1.getJPOProgram("Test1").getName() + "_mxJPO {}");

        final DataCollection data2 = new DataCollection(this);
        final String prgName2 = data2.getJPOProgram("test.subtest.Test2").getName();
        data2.getJPOProgram("test.subtest.Test2")
             .setCode("package " + prgName2.replaceAll("\\.[^.]*$", "") + ";\n"
                     + "public class " + prgName2.replaceAll(".*\\.", "") + "_mxJPO {}");

        return new Object[][]  {
                new Object[]{
                        "default package",
                        data1.getJPOProgram("Test1")},
                new Object[]{
                        "with sub package",
                        data2.getJPOProgram("test.subtest.Test2")},
                new Object[]{
                        "with CDATA tag end",
                        new JPOProgramData(this, "Test1").setCode("<]]>")},
                new Object[]{
                        "with user",
                        new JPOProgramData(this, "Test1").setCode("test").setUser(new PersonAdminData(this, "Test \"Person\""))},
                new Object[]{
                        "with description",
                        new JPOProgramData(this, "Test1").setCode("test").setValue("description", "Test \"Description\"")},
                new Object[]{
                        "with hidden flag",
                        new JPOProgramData(this, "Test1").setCode("test").setHidden(true)},
                new Object[]{
                        "with back slashes",
                        new JPOProgramData(this, "Test1").setCode("public class ${CLASSNAME} { final String str = \"\\\"\";}")},
                new Object[]{
                        "with execution deferred",
                        new JPOProgramData(this, "Test1").setCode("test").setDeferred(true)},
        };
    }

    /**
     * Cleanups the MX system by deleting the test JPOs.
     *
     * @throws Exception if cleanup failed
     */
    @BeforeMethod()
    @AfterMethod()
    public void cleanup()
        throws Exception
    {
        this.cleanup(CI.PRG_JPO);
        this.cleanup(CI.USR_PERSONADMIN);
    }

    /**
     * Checks that the new created JPO is exported correctly.
     *
     * @param _description  description of the test data
     * @param _jpoProgram   data collection to test
     * @throws Exception if test failed
     */
    @Test(dataProvider = "jpos", description = "test export of JPOs")
    public void simpleExport(final String _description,
                             final JPOProgramData _jpoProgram)
        throws Exception
    {
        _jpoProgram.create();
        final ExportParser exportParser = _jpoProgram.export();
        _jpoProgram.checkExport(exportParser);

        // check if package definition exists / not exists
        Assert.assertEquals((exportParser.getOrigCode().indexOf("package") >= 0),
                            (_jpoProgram.getCode().indexOf("package") >= 0),
                            "checks that JPO code has correct package definition");
    }

    /**
     * Tests, if the JPO within MX is created and registered with the correct
     * symbolic name.
     *
     * @param _description  description of the test data
     * @param _jpoProgram   data collection to test
     * @throws Exception if test failed
     */
    @Test(dataProvider = "jpos", description = "test update of non existing JPOs")
    public void simpleUpdate(final String _description,
                             final JPOProgramData _jpoProgram)
        throws Exception
    {
        // create user
        if (_jpoProgram.getUser() != null)  {
            _jpoProgram.getUser().create();
        }
        // create referenced property value
        for (final PropertyDef prop : _jpoProgram.getProperties())  {
            if (prop.getTo() != null)  {
                prop.getTo().create();
            }
        }

        // first update with original content
        this.update(_jpoProgram);

        // the replace code (removing TCL update commands)
        final StringBuilder cmd = new StringBuilder()
                .append("escape mod program \"").append(AbstractTest.convertMql(_jpoProgram.getName()))
                .append("\" code \"").append(AbstractTest.convertMql(_jpoProgram.getCode())).append("\"");
        this.mql(cmd);

        // export
        final ExportParser exportParser = _jpoProgram.export();
        _jpoProgram.checkExport(exportParser);

        // set creator as user, 'test' as description, not hidden
        this.mql("escape mod program \"" + AbstractTest.convertMql(_jpoProgram.getName())
                + "\" execute user creator execute immediate !needsbusinessobject !downloadable !pipe !pooled description test !hidden");

        // second update with delivered content
        this.update(_jpoProgram.getCIFileName(), exportParser.getOrigCode());
        _jpoProgram.checkExport(_jpoProgram.export());

        // third update with delivered content (without changing the code)
        this.update(_jpoProgram.getCIFileName(), exportParser.getOrigCode());
        _jpoProgram.checkExport(_jpoProgram.export());

        Assert.assertEquals(exportParser.getOrigCode(),
                            _jpoProgram.export().getOrigCode(),
                            "check that separators correct removed");
    }

    /**
     * Check that the end tag of CDATA is correct translated to
     * 'Inserted_by_ENOVIA'.
     *
     * @throws Exception if test failed
     */
    @Test(description = "check that the end tag of CDATA is correct translated to 'Inserted_by_ENOVIA'")
    public void checkCDataTranslation()
        throws Exception
    {
        final String name = AbstractTest.PREFIX + "_Test";
        this.mql("add prog " + name + " java code '<]]>'");
        final String xml = this.mql("export prog " + name + " xml");
        Assert.assertTrue(xml.indexOf("<code><![CDATA[<]Inserted_by_ENOVIA]Inserted_by_ENOVIA>]]></code>") >= 0,
                          "check translation of the CDATA conversion");
    }
}
