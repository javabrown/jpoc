import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.print.DocPrintJob;

public class ClipboardDemo {
	public static void main(String[] args) {
		setClipboardContents("Hello World");
		System.out.println(getClipboardContents());
	}

	public static void sample() throws UnsupportedFlavorException, IOException {
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		System.out.println("Object Name: " + clip.getName());
		Transferable contents = clip
				.getContents(new ClipboardDemo().getClass());
		if (contents == null)
			System.out.println("\n\nThe clipboard is empty.");
		else {
			DataFlavor flavors[] = contents.getTransferDataFlavors();
			for (int i = 0; i < flavors.length; ++i) {
				Object obj = contents.getTransferData(flavors[i]);
				System.out.println("DATA=" + obj.toString() + "\n");
				System.out.println("\n\n Name: "
						+ flavors[i].getHumanPresentableName());
				System.out.println("\n MIME Type: " + flavors[i].getMimeType());
				Class cl = flavors[i].getRepresentationClass();
				if (cl == null)
					System.out.println("null");
				else
					System.out.println(cl.getName());
			}
		}
	}

	public static void setClipboardContents(String content) {
		StringSelection stringSelection = new StringSelection(content);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);
	}

	public static String getClipboardContents() {
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// odd: the Object param of getContents is not currently used
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null)
				&& contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText) {
			try {
				result = (String) contents
						.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException | IOException ex) {
				System.out.println(ex);
				ex.printStackTrace();
			}
		}

		return result;
	}
}