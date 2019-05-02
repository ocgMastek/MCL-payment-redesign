package com.qrcode.qrcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.ws.rs.FormParam;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import myapp.QR;

public class QR extends Payment{
	
	@FormParam("HPP_CUSTOMER_EMAIL")
	String customerEmail;
	
	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	

	public static void main(String[] args) throws WriterException, IOException,
	NotFoundException {
		QR qr = new QR();
		qr.setMerch_ID("mastek");
		qr.setCurrencyType("EUR");
		qr.setAmount("2322");
		qr.setCustomerEmail("test@test.com");
//		System.out.print(qr.getOrder_ID());
		
//		String fileName = "CoolCoolCool.png";
		String qrCodeData = "https://pay.sandbox.realexpayments.com/pay?TIMESTAMP="+qr.getTimestamp()+"&MERCHANT_ID="+qr.getMerch_ID()+"&ORDER_ID="+qr.getOrder_ID()+"&AMOUNT="+qr.getAmount()+"&CURRENCY="+qr.getCurrencyType()+"&SHA1HASH="+qr.getSHA1HASH()+"&HPP_CUSTOMER_EMAIL="+qr.getCustomerEmail();
		String filePath = "C:\\\\Users\\\\eleanor61082\\QRCODES/"+qr.getTimestamp()+".png";
		String charset = "UTF-8"; // or "ISO-8859-1" variable width character encoding capable of encoding all 1,112,064 valid code points
		Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		createQRCode(qrCodeData, filePath, charset, hintMap, 200, 200); //'200' is the QR code height and width
			System.out.println("QR Code image created successfully!");

			System.out.println("Data read from QR Code: "
					+ readQRCode(filePath, charset, hintMap));

	}

	public static void createQRCode(String qrCodeData, String filePath,
			String charset, Map hintMap, int qrCodeheight, int qrCodewidth)
					throws WriterException, IOException {
					BitMatrix matrix = new MultiFormatWriter().encode( //'BitMatrix' is a dictionary, 2D array in which each element is equal to 0-1
		//QR code being a popular two-dimensional barcode
					new String(qrCodeData.getBytes(charset), charset),
					BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
					MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
							.lastIndexOf('.') + 1), new File(filePath));
	}

	public static String readQRCode(String filePath, String charset, Map hintMap)
			throws FileNotFoundException, IOException, NotFoundException {
			BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
			new BufferedImageLuminanceSource(
				ImageIO.read(new FileInputStream(filePath)))));
			Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
				hintMap);
			return qrCodeResult.getText();
	}
	
	

}
