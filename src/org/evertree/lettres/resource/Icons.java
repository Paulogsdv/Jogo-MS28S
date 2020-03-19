package org.evertree.lettres.resource;

import java.awt.Image;
import java.awt.Toolkit;

public class Icons {

    public final static Image LETTRES_16 = Toolkit
	    .getDefaultToolkit()
	    .getImage(
		    ClassLoader
			    .getSystemResource("org/evertree/lettres/resource/lettres_16_16.gif"));

    public final static Image LETTRES_32 = Toolkit
	    .getDefaultToolkit()
	    .getImage(
		    ClassLoader
			    .getSystemResource("org/evertree/lettres/resource/lettres_32_32.gif"));

}
