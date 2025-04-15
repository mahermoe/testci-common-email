package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {
	
	private static final String[] TEST_EMAILS = {"ab@bc.com", "a.b@c.org", "abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd"};
	
	private EmailConcrete email;
	
	@Before
	public void setUpEmailTest() throws Exception {
		email = new EmailConcrete();
	}
	
	@After
	public void tearDownEmailTest() throws Exception {
		
	}
	
	// Tests if BCC addresses are added correctly and the size of the BCC list is updated
	@Test
	public void testAddBcc() throws Exception {
		email.addBcc(TEST_EMAILS);
		assertEquals(3, email.getBccAddresses().size());
		
	}
	
	// Tests if a CC address is added correctly and the size of the CC list is updated
	@Test
    public void testAddCc() throws EmailException {
        email.addCc("blah@example.com");
        assertEquals(1, email.getCcAddresses().size());
    }

	// Tests if a header is added correctly and handles IllegalArgumentException when name is null
	@Test
    public void testAddHeader() {
        String name = "Test1";
        String value = "Test2";

        email.addHeader(name, value);

        assertEquals("Test2", email.headers.get(name));
        
        try {
        	email.addHeader(null, "something");
        	
        } catch (IllegalArgumentException exc) {
        	
        }
    }
	
	// Tests if the reply-to addresses are added correctly and the size of the reply-to list is updated
	@Test
    public void testAddReplyTo() throws Exception {
        email.addReplyTo("emailaddress@address.com", "hello");
        assertEquals(1, email.getReplyToAddresses().size());
    }
	
	// Tests if the hostname is set and retrieved correctly
	@Test
	public void testGetHostName() throws Exception {
		String hostname = "ahostname.com";
		email.setHostName(hostname);
		assertEquals(hostname, email.getHostName());
	}
	
	// Tests if the hostname is null and ensures getHostName returns null
	@Test
	public void testGetHostName2() throws Exception {
		email.setHostName(null);
		String hostname = email.getHostName();
		assertNull(hostname);
	}
	
	// Tests if a valid mail session is returned after setting up
	@Test
	public void testGetMailSession() throws Exception {
		email.setHostName("hostname.com");
		email.setSSLOnConnect(true);
		assertNotNull(email.getMailSession());
	}

	// Tests if the sent date is correctly set and retrieved
	@Test
	public void testGetSentDate() throws Exception {
		Date someDate = new Date();
		email.setSentDate(someDate);
		assertEquals(email.getSentDate(), someDate);
	}

	// Tests if the socket connection timeout is set and retrieved correctly
	@Test
	public void testGetSocketConnectionTimeout() throws Exception {
		int exampleconnectiontimeout = 40;
		email.setSocketConnectionTimeout(exampleconnectiontimeout);
		assertEquals(exampleconnectiontimeout, email.getSocketConnectionTimeout());
	}
	
	// Tests if from email addresss is set and retrieved correctly
	@Test
	public void testSetFrom() throws Exception {
		String test = "myemailtest@something.com";
		email.setFrom(test);
		assertEquals(test, email.getFromAddress().getAddress());
	}
	
	
	
	@Test
	(expected = RuntimeException.class)
	public void testbuildMimeMessageNullCheck() throws Exception {
		try {
				email.setHostName("localhost");
				email.setSmtpPort(1234);
				email.setFrom("a@b.com");
				email.addTo("c@d.com");
				email.setSubject("test mail");
				email.setCharset("ISO-8859-1");
				email.setContent("test content", "text/plain");
				email.buildMimeMessage();
				email.buildMimeMessage();
		} catch (RuntimeException re) {
			String message = "The MimeMessage is already built.";
			assertEquals(message, re.getMessage());
			throw re;
		}
	}
	
	
	// Tests that MimeMessage is built successfully with valid input
	@Test
	public void testBuildMimeMessage() throws Exception {
	    EmailConcrete email2 = new EmailConcrete();
	    
	    email2.setFrom("something@something.com");
	    email2.addTo("something2@something.com");
	    email2.setHostName("something host");
	    email2.setSmtpPort(1234);
	    email2.setMsg("something message");
	    email2.addCc("something3@something.com");
	    email2.addBcc("something4@something.com");
	    email2.setSubject("something subject");

	    email2.buildMimeMessage();

	    assertNotNull(email2.getMimeMessage()); 
	}
	
	// Tests MimeMessage creation with null content
	@Test
	public void testBuildMimeMessage2() throws Exception {
	    EmailConcrete email3 = new EmailConcrete();
	    
	    email3.setFrom("something@something.com");
	    email3.addTo("something2@something.com");
	    email3.setHostName("something host");
	    email3.setSmtpPort(123);
	    email3.setSubject("Test Subject");
	    
	    email3.setContent(null, "text");
	    email3.buildMimeMessage();

	    assertNotNull(email3.getMimeMessage());
	}
	
//	// Tests mime with no address. error
//	@Test
//	public void testBuildMimeMessage3() throws Exception {
//	    EmailConcrete email4 = new EmailConcrete();
//	    
//	    email4.setFrom("something@something.com");
//	    email4.setHostName("something2@something.com");
//	    email4.setSmtpPort(321);
//	    email4.setSubject("Something Subject");
//	    
//	    email4.buildMimeMessage();
//	    
//	    assertNotNull(email4.getMimeMessage());
//	}
	
}
