/*
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.idrsolutions.com
 * Help section for developers at http://www.idrsolutions.com/java-pdf-library-support/
 *
 * (C) Copyright 1997-2013, IDRsolutions and Contributors.
 *
 * 	This file is part of JPedal
 *
     This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


 *
 * ---------------
 * GroupingObject.java
 * ---------------
 */
package org.jpedal.objects.raw;

import org.jpedal.color.ColorSpaces;
import org.jpedal.utils.LogWriter;

public class GroupingObject extends PdfObject {

	// unknown CMAP as String
	// String unknownValue=null;

	private String Name = null;

	private byte[] rawName = null;

	private int colorType = PdfDictionary.Unknown;

	private PdfObject colorSpace;

	public GroupingObject(String ref) {
		super(ref);
	}

	public GroupingObject(int ref, int gen) {
		super(ref, gen);
	}

	public GroupingObject(int ref, int gen, boolean isIndexed) {

		super(ref, gen);

		this.isIndexed = isIndexed;
	}

	public GroupingObject(int type) {
		super(type);
	}

	public GroupingObject(byte[] bytes) {

		super(bytes);

		// set ID
		this.colorType = PdfDictionary.getIntKey(0, bytes.length, bytes);
	}

	@Override
	public byte[][] getStringArray(int id) {

		switch (id) {

			default:
				return super.getStringArray(id);
		}
	}

	@Override
	public void setStringArray(int id, byte[][] value) {

		switch (id) {

			default:
				super.setStringArray(id, value);
		}
	}

	@Override
	public boolean getBoolean(int id) {

		switch (id) {

		// case PdfDictionary.ImageMask:
		// return ImageMask;
		//

			default:
				return super.getBoolean(id);
		}
	}

	@Override
	public void setBoolean(int id, boolean value) {

		switch (id) {

		// case PdfDictionary.ImageMask:
		// ImageMask=value;
		// break;

			default:
				super.setBoolean(id, value);
		}
	}

	@Override
	public PdfObject getDictionary(int id) {

		switch (id) {

			case PdfDictionary.ColorSpace:
				return this.colorSpace;

			default:
				return super.getDictionary(id);
		}
	}

	@Override
	public void setIntNumber(int id, int value) {

		switch (id) {

			default:
				super.setIntNumber(id, value);
		}
	}

	@Override
	public int getInt(int id) {

		switch (id) {

			default:
				return super.getInt(id);
		}
	}

	@Override
	public void setDictionary(int id, PdfObject value) {

		value.setID(id);

		switch (id) {

			case PdfDictionary.ColorSpace:
				this.colorSpace = value;

				break;

			default:
				super.setDictionary(id, value);
		}
	}

	@Override
	public int setConstant(int pdfKeyType, int keyStart, int keyLength, byte[] raw) {

		int PDFvalue = PdfDictionary.Unknown;

		int id = 0, x = 0, next;

		try {

			// convert token to unique key which we can lookup

			for (int i2 = keyLength - 1; i2 > -1; i2--) {

				next = raw[keyStart + i2];

				next = next - 48;

				id = id + ((next) << x);

				x = x + 8;
			}

			switch (id) {

			// case PdfDictionary.Image:
			// PDFvalue =PdfDictionary.Image;
			// break;
			//
			// case PdfDictionary.Form:
			// PDFvalue =PdfDictionary.Form;
			// break;

				case PdfDictionary.G:
					PDFvalue = ColorSpaces.DeviceGray;
					break;

				case PdfDictionary.RGB:
					PDFvalue = ColorSpaces.DeviceRGB;
					break;

				default:

					// if(pdfKeyType==PdfDictionary.Encoding){
					// PDFvalue=PdfCIDEncodings.getConstant(id);
					//
					// if(PDFvalue==PdfDictionary.Unknown){
					//
					// byte[] bytes=new byte[keyLength];
					//
					// System.arraycopy(raw,keyStart,bytes,0,keyLength);
					//
					// unknownValue=new String(bytes);
					// }
					//
					// if(debug && PDFvalue==PdfDictionary.Unknown){
					// System.out.println("Value not in PdfCIDEncodings");
					//
					// byte[] bytes=new byte[keyLength];
					//
					// System.arraycopy(raw,keyStart,bytes,0,keyLength);
					// System.out.println("Add to CIDEncodings and as String");
					// System.out.println("key="+new String(bytes)+" "+id+" not implemented in setConstant in PdfFont Object");
					//
					// System.out.println("final public static int CMAP_"+new String(bytes)+"="+id+";");
					// }
					// }else
					PDFvalue = super.setConstant(pdfKeyType, id);

					if (PDFvalue == -1) {

						if (debug) {

							byte[] bytes = new byte[keyLength];

							System.arraycopy(raw, keyStart, bytes, 0, keyLength);
							System.out.println("key=" + new String(bytes) + ' ' + id + " not implemented in setConstant in " + this);

							System.out.println("final public static int " + new String(bytes) + '=' + id + ';');

						}
					}

					break;

			}

		}
		catch (Exception e) {
			// tell user and log
			if (LogWriter.isOutput()) LogWriter.writeLog("Exception: " + e.getMessage());
		}

		// System.out.println(pdfKeyType+"="+PDFvalue);
		switch (pdfKeyType) {

			case PdfDictionary.ColorSpace:
				this.colorType = PDFvalue;
				break;

		}

		return PDFvalue;
	}

	@Override
	public int getParameterConstant(int key) {
		int def = PdfDictionary.Unknown;

		switch (key) {

			case PdfDictionary.ColorSpace:

				return this.colorType;

			default:
				super.getParameterConstant(key);
		}

		return def;
	}

	// public void setStream(){
	//
	// hasStream=true;
	// }

	@Override
	public PdfArrayIterator getMixedArray(int id) {

		switch (id) {

		// case PdfDictionary.Differences:
		// return new PdfArrayIterator(Differences);

			default:
				return super.getMixedArray(id);
		}
	}

	@Override
	public double[] getDoubleArray(int id) {

		switch (id) {
			default:
				return super.getDoubleArray(id);
		}
	}

	@Override
	public void setDoubleArray(int id, double[] value) {

		switch (id) {
			default:
				super.setDoubleArray(id, value);
		}
	}

	/**
	 * set values (access with getArrayIterator(int id))
	 */
	@Override
	public void setMixedArray(int id, byte[][] value) {

		switch (id) {

		// case PdfDictionary.Differences:
		// Differences=value;
		// break;

			default:
				super.setMixedArray(id, value);
		}
	}

	@Override
	public float[] getFloatArray(int id) {

		switch (id) {

			default:
				return super.getFloatArray(id);

		}
	}

	@Override
	public void setFloatNumber(int id, float value) {

		switch (id) {

			default:
				super.setFloatNumber(id, value);
		}
	}

	@Override
	public float getFloatNumber(int id) {

		switch (id) {

			default:
				return super.getFloatNumber(id);
		}
	}

	@Override
	public void setFloatArray(int id, float[] value) {

		switch (id) {

			default:
				super.setFloatArray(id, value);
		}
	}

	@Override
	public void setName(int id, byte[] value) {

		switch (id) {

			case PdfDictionary.Name:
				this.rawName = value;
				break;

			default:
				super.setName(id, value);

		}
	}

	@Override
	public byte[] getStringValueAsByte(int id) {

		switch (id) {

			case PdfDictionary.Name:
				return this.rawName;

			default:
				return super.getStringValueAsByte(id);

		}
	}

	@Override
	public void setTextStreamValue(int id, byte[] value) {

		switch (id) {

		// case PdfDictionary.CharSet:
		// rawCharSet=value;
		// break;
		//

			default:
				super.setTextStreamValue(id, value);

		}
	}

	@Override
	public String getName(int id) {

		switch (id) {

			case PdfDictionary.Name:

				// setup first time
				if (this.Name == null && this.rawName != null) this.Name = new String(this.rawName);

				return this.Name;

			default:
				return super.getName(id);

		}
	}

	@Override
	public String getTextStreamValue(int id) {

		switch (id) {

		// case PdfDictionary.CharSet:
		//
		// //setup first time
		// if(CharSet==null && rawCharSet!=null)
		// CharSet=new String(rawCharSet);
		//
		// return CharSet;

			default:
				return super.getTextStreamValue(id);

		}
	}

	/**
	 * unless you need special fucntions, use getStringValue(int id) which is faster
	 */
	@Override
	public String getStringValue(int id, int mode) {

		byte[] data = null;

		// get data
		switch (id) {

		// case PdfDictionary.BaseFont:
		// data=rawBaseFont;
		// break;

		}

		// convert
		switch (mode) {
			case PdfDictionary.STANDARD:

				// setup first time
				if (data != null) return new String(data);
				else return null;

			case PdfDictionary.LOWERCASE:

				// setup first time
				if (data != null) return new String(data);
				else return null;

			case PdfDictionary.REMOVEPOSTSCRIPTPREFIX:

				// setup first time
				if (data != null) {
					int len = data.length;
					if (len > 6 && data[6] == '+') { // lose ABCDEF+ if present
						int length = len - 7;
						byte[] newData = new byte[length];
						System.arraycopy(data, 7, newData, 0, length);
						return new String(newData);
					}
					else return new String(data);
				}
				else return null;

			default:
				throw new RuntimeException("Value not defined in getName(int,mode) in " + this);
		}
	}

	@Override
	public int getObjectType() {
		return PdfDictionary.Group;
	}

	@Override
	public boolean decompressStreamWhenRead() {
		return true;
	}
}