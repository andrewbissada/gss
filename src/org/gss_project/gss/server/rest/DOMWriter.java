/*
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

package org.gss_project.gss.server.rest;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A sample DOM writer. This sample program illustrates how to
 * traverse a DOM tree in order to print a document that is parsed.
 */
public class DOMWriter {

	//
	// Data
	//

	/** Default Encoding */
	private static  String
	PRINTWRITER_ENCODING = "UTF8";

	/**
	 *
	 */
	private static String MIME2JAVA_ENCODINGS[] =
	{ "Default", "UTF-8", "US-ASCII", "ISO-8859-1", "ISO-8859-2", "ISO-8859-3", "ISO-8859-4",
		"ISO-8859-5", "ISO-8859-6", "ISO-8859-7", "ISO-8859-8", "ISO-8859-9", "ISO-2022-JP",
		"SHIFT_JIS", "EUC-JP","GB2312", "BIG5", "EUC-KR", "ISO-2022-KR", "KOI8-R", "EBCDIC-CP-US",
		"EBCDIC-CP-CA", "EBCDIC-CP-NL", "EBCDIC-CP-DK", "EBCDIC-CP-NO", "EBCDIC-CP-FI", "EBCDIC-CP-SE",
		"EBCDIC-CP-IT", "EBCDIC-CP-ES", "EBCDIC-CP-GB", "EBCDIC-CP-FR", "EBCDIC-CP-AR1",
		"EBCDIC-CP-HE", "EBCDIC-CP-CH", "EBCDIC-CP-ROECE","EBCDIC-CP-YU",
		"EBCDIC-CP-IS", "EBCDIC-CP-AR2", "UTF-16"
	};

	/** Output qualified names */
	private boolean qualifiedNames = true;

	/** Print writer. */
	protected PrintWriter out;

	/** Canonical output. */
	protected boolean canonical;


	/**
	 * @param encoding
	 * @param _canonical
	 * @throws UnsupportedEncodingException
	 */
	public DOMWriter(String encoding, boolean _canonical)
	throws UnsupportedEncodingException {
		out = new PrintWriter(new OutputStreamWriter(System.out, encoding));
		canonical = _canonical;
	} // <init>(String,boolean)

	//
	// Constructors
	//

	/** Default constructor.
	 * @param _canonical
	 * @throws UnsupportedEncodingException
	 */
	public DOMWriter(boolean _canonical) throws UnsupportedEncodingException {
		this( getWriterEncoding(), _canonical);
	}

	/**
	 * @param writer
	 * @param _canonical
	 */
	public DOMWriter(Writer writer, boolean _canonical) {
		out = new PrintWriter(writer);
		canonical = _canonical;
	}

	/**
	 * @return the qualifiedNames
	 */
	public boolean getQualifiedNames() {
		return qualifiedNames;
	}

	/**
	 * @param _qualifiedNames
	 */
	public void setQualifiedNames(boolean _qualifiedNames) {
		qualifiedNames = _qualifiedNames;
	}

	/**
	 * @return the PrintWriter encoding
	 */
	public static String getWriterEncoding( ) {
		return PRINTWRITER_ENCODING;
	}// getWriterEncoding

	/**
	 * @param encoding
	 */
	public static void  setWriterEncoding( String encoding ) {
		if( encoding.equalsIgnoreCase( "DEFAULT" ) )
			PRINTWRITER_ENCODING  = "UTF8";
		else if( encoding.equalsIgnoreCase( "UTF-16" ) )
			PRINTWRITER_ENCODING  = "Unicode";
		else
			PRINTWRITER_ENCODING = MIME2Java.convert(encoding);
	}// setWriterEncoding


	/**
	 * @param encoding
	 * @return true if the encoding is valid
	 */
	public static boolean isValidJavaEncoding( String encoding ) {
		for ( int i = 0; i < MIME2JAVA_ENCODINGS.length; i++ )
			if ( encoding.equals( MIME2JAVA_ENCODINGS[i] ) )
				return true;

		return false;
	}// isValidJavaEncoding


	/** Prints the specified node, recursively.
	 * @param node
	 */
	public void print(Node node) {

		// is there anything to do?
		if ( node == null )
			return;

		int type = node.getNodeType();
		switch ( type ) {
			// print document
			case Node.DOCUMENT_NODE: {
				if ( !canonical ) {
					String  Encoding = getWriterEncoding();
					if( Encoding.equalsIgnoreCase( "DEFAULT" ) )
						Encoding = "UTF-8";
					else if( Encoding.equalsIgnoreCase( "Unicode" ) )
						Encoding = "UTF-16";
					else
						Encoding = MIME2Java.reverse( Encoding );

					out.println("<?xml version=\"1.0\" encoding=\""+
								Encoding + "\"?>");
				}
				print(((Document)node).getDocumentElement());
				out.flush();
				break;
			}

			// print element with attributes
			case Node.ELEMENT_NODE: {
				out.print('<');
				if (qualifiedNames)
					out.print(node.getNodeName());
				else
					out.print(node.getLocalName());
				Attr attrs[] = sortAttributes(node.getAttributes());
				for ( int i = 0; i < attrs.length; i++ ) {
					Attr attr = attrs[i];
					out.print(' ');
					if (qualifiedNames)
						out.print(attr.getNodeName());
					else
						out.print(attr.getLocalName());

					out.print("=\"");
					out.print(normalize(attr.getNodeValue()));
					out.print('"');
				}
				out.print('>');
				NodeList children = node.getChildNodes();
				if ( children != null ) {
					int len = children.getLength();
					for ( int i = 0; i < len; i++ )
						print(children.item(i));
				}
				break;
			}

			// handle entity reference nodes
			case Node.ENTITY_REFERENCE_NODE: {
				if ( canonical ) {
					NodeList children = node.getChildNodes();
					if ( children != null ) {
						int len = children.getLength();
						for ( int i = 0; i < len; i++ )
							print(children.item(i));
					}
				} else {
					out.print('&');
					if (qualifiedNames)
						out.print(node.getNodeName());
					else
						out.print(node.getLocalName());
					out.print(';');
				}
				break;
			}

			// print cdata sections
			case Node.CDATA_SECTION_NODE: {
				if ( canonical )
					out.print(normalize(node.getNodeValue()));
				else {
					out.print("<![CDATA[");
					out.print(node.getNodeValue());
					out.print("]]>");
				}
				break;
			}

			// print text
			case Node.TEXT_NODE: {
				out.print(normalize(node.getNodeValue()));
				break;
			}

			// print processing instruction
			case Node.PROCESSING_INSTRUCTION_NODE: {
				out.print("<?");
				if (qualifiedNames)
					out.print(node.getNodeName());
				else
					out.print(node.getLocalName());

				String data = node.getNodeValue();
				if ( data != null && data.length() > 0 ) {
					out.print(' ');
					out.print(data);
				}
				out.print("?>");
				break;
			}
		}

		if ( type == Node.ELEMENT_NODE ) {
			out.print("</");
			if (qualifiedNames)
				out.print(node.getNodeName());
			else
				out.print(node.getLocalName());
			out.print('>');
		}

		out.flush();

	} // print(Node)

	/** Returns a sorted list of attributes.
	 * @param attrs
	 * @return
	 */
	@SuppressWarnings("null")
	protected Attr[] sortAttributes(NamedNodeMap attrs) {

		int len = attrs != null ? attrs.getLength() : 0;
		Attr array[] = new Attr[len];
		for ( int i = 0; i < len; i++ )
			array[i] = (Attr)attrs.item(i);
		for ( int i = 0; i < len - 1; i++ ) {
			String name = null;
			if (qualifiedNames)
				name  = array[i].getNodeName();
			else
				name  = array[i].getLocalName();
			int    index = i;
			for ( int j = i + 1; j < len; j++ ) {
				String curName = null;
				if (qualifiedNames)
					curName = array[j].getNodeName();
				else
					curName = array[j].getLocalName();
				if ( curName.compareTo(name) < 0 ) {
					name  = curName;
					index = j;
				}
			}
			if ( index != i ) {
				Attr temp    = array[i];
				array[i]     = array[index];
				array[index] = temp;
			}
		}

		return array;

	} // sortAttributes(NamedNodeMap):Attr[]


	/** Normalizes the given string.
	 * @param s
	 * @return
	 */
	@SuppressWarnings({"fallthrough", "null"})
	protected String normalize(String s) {
		StringBuffer str = new StringBuffer();

		int len = s != null ? s.length() : 0;
		for ( int i = 0; i < len; i++ ) {
			char ch = s.charAt(i);
			switch ( ch ) {
				case '<': {
					str.append("&lt;");
					break;
				}
				case '>': {
					str.append("&gt;");
					break;
				}
				case '&': {
					str.append("&amp;");
					break;
				}
				case '"': {
					str.append("&quot;");
					break;
				}
				case '\r':
				case '\n': {
					if ( canonical ) {
						str.append("&#");
						str.append(Integer.toString(ch));
						str.append(';');
						break;
					}
					// else, default append char
				}
				default: {
					str.append(ch);
				}
			}
		}

		return str.toString();

	} // normalize(String):String

	/**
	 *
	 */
	@SuppressWarnings("unused")
	private static void printValidJavaEncoding() {
		System.err.println( "    ENCODINGS:" );
		System.err.print( "   " );
		for( int i = 0;
		i < MIME2JAVA_ENCODINGS.length; i++) {
			System.err.print( MIME2JAVA_ENCODINGS[i] + " " );
			if( i % 7 == 0 ){
				System.err.println();
				System.err.print( "   " );
			}
		}

	} // printJavaEncoding()

}
