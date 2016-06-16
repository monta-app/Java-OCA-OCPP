package eu.chargetime.ocpp.model.test;

import eu.chargetime.ocpp.model.AuthorizeConfirmation;
import eu.chargetime.ocpp.model.IdTagInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 ChargeTime.eu - Java-OCA-OCPP
 Copyright (C) 2015-2016 Thomas Volden <tv@chargetime.eu>

 MIT License

 Copyright (c) 2016 Thomas Volden

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
public class AuthorizeConfirmationTest {
    AuthorizeConfirmation confirmation;

    @Mock
    IdTagInfo infoTag = mock(IdTagInfo.class);

    @Before
    public void setup() throws Exception {

        confirmation = new AuthorizeConfirmation();
    }

    @Test
    public void validate_idTagInfoIsNull_returnsFalse() {
        // when
        boolean isValid = confirmation.validate();

        // Then
        assertThat(isValid, is(false));
    }

    @Test
    public void validate_idTagInfoSet_callsValidateOnIdTagInfo() {
        // Given
        confirmation.setIdTagInfo(infoTag);

        // When
        confirmation.validate();

        // Then
        verify(infoTag, times(1)).validate();
    }

}