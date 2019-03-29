


class ScanResult {

  String text;
  BarcodeFormat format;

  ScanResult(this.text, String format) {

    switch (format) {
      case "AZTEC":
        this.format = BarcodeFormat.AZTEC;
        break;
      case "CODABAR":
        this.format = BarcodeFormat.CODABAR;
        break;
      case "CODE_39":
        this.format = BarcodeFormat.CODE_39;
        break;
      case "CODE_93":
        this.format = BarcodeFormat.CODE_93;
        break;
      case "CODE_128":
        this.format = BarcodeFormat.CODE_128;
        break;
      case "DATA_MATRIX":
        this.format = BarcodeFormat.DATA_MATRIX;
        break;
      case "EAN_8":
        this.format = BarcodeFormat.EAN_8;
        break;
      case "EAN_13":
        this.format = BarcodeFormat.EAN_13;
        break;
      case "ITF":
        this.format = BarcodeFormat.ITF;
        break;
      case "MAXICODE":
        this.format = BarcodeFormat.MAXICODE;
        break;
      case "PDF_417":
        this.format = BarcodeFormat.PDF_417;
        break;
      case "QR_CODE":
        this.format = BarcodeFormat.QR_CODE;
        break;
      case "RSS_14":
        this.format = BarcodeFormat.RSS_14;
        break;
      case "RSS_EXPANDED":
        this.format = BarcodeFormat.RSS_EXPANDED;
        break;
      case "UPC_A":
        this.format = BarcodeFormat.UPC_A;
        break;
      case "UPC_E":
        this.format = BarcodeFormat.UPC_E;
        break;
      case "UPC_EAN_EXTENSION":
        this.format = BarcodeFormat.UPC_EAN_EXTENSION;
        break;
    }
  }


}

enum BarcodeFormat {

  /** Aztec 2D barcode format. */
  AZTEC,

  /** CODABAR 1D format. */
  CODABAR,

  /** Code 39 1D format. */
  CODE_39,

  /** Code 93 1D format. */
  CODE_93,

  /** Code 128 1D format. */
  CODE_128,

  /** Data Matrix 2D barcode format. */
  DATA_MATRIX,

  /** EAN-8 1D format. */
  EAN_8,

  /** EAN-13 1D format. */
  EAN_13,

  /** ITF (Interleaved Two of Five) 1D format. */
  ITF,

  /** MaxiCode 2D barcode format. */
  MAXICODE,

  /** PDF417 format. */
  PDF_417,

  /** QR Code 2D barcode format. */
  QR_CODE,

  /** RSS 14 */
  RSS_14,

  /** RSS EXPANDED */
  RSS_EXPANDED,

  /** UPC-A 1D format. */
  UPC_A,

  /** UPC-E 1D format. */
  UPC_E,

  /** UPC/EAN extension format. Not a stand-alone format. */
  UPC_EAN_EXTENSION

}