package com.ssctrl.interface4;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.ByteArrayInputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import android.content.res.AXmlResourceParser;





public class ApkInfo {
	
	private static final Namespace NS = Namespace.getNamespace("http://schemas.android.com/apk/res/android");
	private Element root;
	
	public ApkInfo(String apkPath) {
		InputStream inputStream = null;
		InputStream xmlInputStream = null;
		ZipFile zipFile = null;
		Document document = null;
		try {
			zipFile = new ZipFile(apkPath);
			ZipEntry zipEntry = new ZipEntry("AndroidManifest.xml");
			inputStream = zipFile.getInputStream(zipEntry);
			AXMLPrinter xmlPrinter = new AXMLPrinter();
			xmlPrinter.startPrinf(inputStream);
			xmlInputStream = new ByteArrayInputStream(xmlPrinter.getBuf().toString().getBytes("UTF-8"));
			
			SAXBuilder builder = new SAXBuilder();
			document = builder.build(xmlInputStream);
			root = document.getRootElement();//跟节点-->manifest
		} catch (IOException | JDOMException e) {
			e.printStackTrace();
			try {
				inputStream.close();
				zipFile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public String getVersionName() {
		if(root == null) {
			return null;
		}
		return root.getAttributeValue("versionName", NS);
	}

//	public static String getApkInfo(String apkPath){
//		ApkInfo apkInfo = new ApkInfo();
//		SAXBuilder builder = new SAXBuilder();
//		Document document = null;
//		try{
//			document = builder.build(getXmlInputStream(apkPath));
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		Element root = document.getRootElement();//跟节点-->manifest
//		apkInfo.setVersionCode(root.getAttributeValue("versionCode",NS));
//		apkInfo.setVersionName(root.getAttributeValue("versionName", NS));
//		apkInfo.setApkPackage(root.getAttributeValue("package", NS));
//		Element elemUseSdk = root.getChild("uses-sdk");//子节点-->uses-sdk
//		apkInfo.setMinSdkVersion(elemUseSdk.getAttributeValue("minSdkVersion", NS));
//		List listPermission = root.getChildren("uses-permission");//子节点是个集合
//		List permissions = new ArrayList();
//		for(Object object : listPermission){
//			String permission = ((Element)object).getAttributeValue("name", NS);
//			permissions.add(permission);
//		}
//		apkInfo.setUses_permission(permissions);
//		
//		String s = root.getAttributes().toString();
//		String c[] = s.split(",");
//		String versionCode = null;
//		String versionName = null;
//		String packageName = null;
//		for(String a: c){
//			if(a.contains("versionCode")){
//				versionCode = a.substring(a.indexOf("versionCode=\"")+13, a.lastIndexOf("\""));
//			}
//			if(a.contains("versionName")){
//				versionName = a.substring(a.indexOf("versionName=\"")+13, a.lastIndexOf("\""));
//			}
//			if(a.contains("package")){
//				packageName = a.substring(a.indexOf("package=\"")+9, a.lastIndexOf("\""));
//			}
//		}
//		System.out.println("\n版本号:"+versionCode+"\n版本名:"+versionName+"\n包名:"+packageName);
//		String str = "\n版本号:"+versionCode+"\n版本名:"+versionName+"\n包名:"+packageName;
////		return root.getAttributes().toString();
//		return str;
//	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File dir = new File("F:\\work_java\\eclipse\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\Business\\apk\\");
		if(!dir.exists()) {
			return;
		}
		File[] files = dir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File arg0) {
				// TODO Auto-generated method stub
				if(arg0.getPath().endsWith(".apk")) {
					return true;
				} else {
					return false;
				}
			}
			
		});
		
		File latestFile = null;
		String latestVersion = null;
		for(File file : files) {
			if(latestFile == null) {
				latestFile = file;
				latestVersion = new ApkInfo(latestFile.getPath()).getVersionName();
			} else {
				String strVersion = new ApkInfo(file.getPath()).getVersionName();
				if(strVersion.compareTo(latestVersion) > 0) {
					latestFile = file;
					latestVersion = strVersion;
				}
			}
		}
		//ApkInfo info = new ApkInfo("F:\\work_java\\eclipse\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\Business\\apk\\Client.apk");
		System.out.println(latestVersion);
	}

}

class AXMLPrinter {

	private static final float RADIX_MULTS[] = { 0.0039063F, 3.051758E-005F, 1.192093E-007F, 4.656613E-010F };
	private static final String DIMENSION_UNITS[] = { "px", "dip", "sp", "pt", "in", "mm", "", "" };
	private static final String FRACTION_UNITS[] = { "%", "%p", "", "", "", "", "", "" };
	private StringBuffer buf;

	public AXMLPrinter() {
		buf = new StringBuffer();
	}

	public StringBuffer getBuf() {
		return buf;
	}

	public void setBuf(StringBuffer buf) {
		this.buf = buf;
	}

	public void startPrinf(InputStream stream) {
		try {
			AXmlResourceParser parser = new AXmlResourceParser();
			parser.open(stream);
			StringBuilder indent = new StringBuilder(10);
			do {
				int type = parser.next();
				if (type == 1)
					break;
				switch (type) {
				case 0: // '\0'
					log("<?xml version=\"1.0\" encoding=\"utf-8\"?>",
							new Object[0]);
					break;

				case 2: // '\002'
					log("%s<%s%s", new Object[] { indent,
							getNamespacePrefix(parser.getPrefix()),
							parser.getName() });
					indent.append("\t");
					int namespaceCountBefore = parser.getNamespaceCount(parser
							.getDepth() - 1);
					int namespaceCount = parser.getNamespaceCount(parser
							.getDepth());
					for (int i = namespaceCountBefore; i != namespaceCount; i++)
						log("%sxmlns:%s=\"%s\"", new Object[] { indent,
								parser.getNamespacePrefix(i),
								parser.getNamespaceUri(i) });

					for (int i = 0; i != parser.getAttributeCount(); i++)
						log("%s%s%s=\"%s\"",
								new Object[] {
										indent,
										getNamespacePrefix(parser
												.getAttributePrefix(i)),
										parser.getAttributeName(i),
										getAttributeValue(parser, i) });

					log("%s>", new Object[] { indent });
					break;

				case 3: // '\003'
					indent.setLength(indent.length() - "\t".length());
					log("%s</%s%s>", new Object[] { indent,
							getNamespacePrefix(parser.getPrefix()),
							parser.getName() });
					break;

				case 4: // '\004'
					log("%s%s", new Object[] { indent, parser.getText() });
					break;
				}
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getNamespacePrefix(String prefix) {
		if (prefix == null || prefix.length() == 0)
			return "";
		else
			return (new StringBuilder(String.valueOf(prefix))).append(":").toString();
	}

	private static String getAttributeValue(AXmlResourceParser parser, int index) {
		int type = parser.getAttributeValueType(index);
		int data = parser.getAttributeValueData(index);
		if (type == 3)
			return parser.getAttributeValue(index);
		if (type == 2)
			return String.format("?%s%08X", new Object[] { getPackage(data), Integer.valueOf(data) });
		if (type == 1)
			return String.format("@%s%08X", new Object[] { getPackage(data), Integer.valueOf(data) });
		if (type == 4)
			return String.valueOf(Float.intBitsToFloat(data));
		if (type == 17)
			return String.format("0x%08X", new Object[] { Integer.valueOf(data) });
		if (type == 18)
			return data == 0 ? "false" : "true";
		if (type == 5)
			return (new StringBuilder(String.valueOf(Float.toString(complexToFloat(data))))).append(DIMENSION_UNITS[data & 0xf]).toString();
		if (type == 6)
			return (new StringBuilder(String.valueOf(Float.toString(complexToFloat(data))))).append(FRACTION_UNITS[data & 0xf]).toString();
		if (type >= 28 && type <= 31)
			return String.format("#%08X", new Object[] { Integer.valueOf(data) });
		if (type >= 16 && type <= 31)
			return String.valueOf(data);
		else
			return String.format("<0x%X, type 0x%02X>", new Object[] {Integer.valueOf(data), Integer.valueOf(type) });
	}

	private static String getPackage(int id) {
		if (id >>> 24 == 1)
			return "android:";
		else
			return "";
	}

	private void log(String format, Object arguments[]) {
		buf.append(String.format(format, arguments));
		buf.append("\n");
	}

	public static float complexToFloat(int complex) {
		return (float) (complex & 0xffffff00) * RADIX_MULTS[complex >> 4 & 3];
	}

}
