/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.file.remote;

import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFileEntryParser;
import org.apache.commons.net.ftp.parser.CompositeFileEntryParser;
import org.apache.commons.net.ftp.parser.NTFTPEntryParser;
import org.apache.commons.net.ftp.parser.UnixFTPEntryParser;
import org.apache.commons.net.ftp.parser.VMSVersioningFTPEntryParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OsgiParserFactoryTest {

    private static final OsgiParserFactory OSGI_PARSER_FACTORY = new OsgiParserFactory(null);
    @Mock
    private FTPClientConfig ftpClientConfig;

    @Before
    public void setup() {
        when(ftpClientConfig.getDefaultDateFormatStr()).thenReturn("yyyy-MM-dd");
    }

    @Test
    public void createFileEntryParserUnix()
            throws Exception {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("bla unix bla");
        FTPFileEntryParser result = OSGI_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(UnixFTPEntryParser.class));
    }

    @Test
    public void createFileEntryParserLinux()
            throws Exception {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("bla linux bla");
        FTPFileEntryParser result = OSGI_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(UnixFTPEntryParser.class));
    }

    @Test
    public void createFileEntryParserTypeL8()
            throws Exception {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("bla type: l8 bla");
        FTPFileEntryParser result = OSGI_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(UnixFTPEntryParser.class));
    }

    @Test
    public void createFileEntryParserVms()
            throws Exception {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("bla vms bla");
        FTPFileEntryParser result = OSGI_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(VMSVersioningFTPEntryParser.class));
    }

    @Test
    public void createFileEntryParserPlainWindows()
            throws Exception {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("WINDOWS");
        FTPFileEntryParser result = OSGI_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(NTFTPEntryParser.class));
    }

    @Test
    public void createFileEntryParserNotPlainWindows()
            throws Exception {
        when(ftpClientConfig.getServerSystemKey()).thenReturn("WINDOWS XP");
        FTPFileEntryParser result = OSGI_PARSER_FACTORY.createFileEntryParser(ftpClientConfig);
        assertThat(result, instanceOf(CompositeFileEntryParser.class));
    }

}