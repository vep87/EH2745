import myPackage.Main;


import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Menu;




public class GUI {

	protected Shell shell;
	
	protected String learningFileName;
	protected String testFileName;
	protected String outputDirectory;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GUI window = new GUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(394, 300);
		shell.setText("EH2745 - Assignment 2");
		
		Label lblChooseOutputDirectory = new Label(shell, SWT.NONE);
		lblChooseOutputDirectory.setText("Choose Output Directory");
		lblChooseOutputDirectory.setBounds(153, 113, 194, 15);
		
		Label lblExecuteAlgothin = new Label(shell, SWT.NONE);
		lblExecuteAlgothin.setText("Execute algorithm");
		lblExecuteAlgothin.setBounds(153, 156, 194, 15);
		
		Label lblLearn = new Label(shell, SWT.NONE);
		lblLearn.setBounds(153, 31, 215, 15);
		lblLearn.setText("Load file with learning values (*.csv)");
		
		Label lblTest = new Label(shell, SWT.NONE);
		lblTest.setBounds(153, 73, 194, 15);
		lblTest.setText("Load file with test values (*.csv)");

		
		Button btnLearnSet = new Button(shell, SWT.FLAT);
		btnLearnSet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
		        fd.setText("Open");
		        fd.setFilterPath("C:/");
		        String[] filterExt = { "*.csv",};
		        fd.setFilterExtensions(filterExt);
		        learningFileName = fd.open();
		        System.out.println(learningFileName);
		        lblLearn.setText("File loaded");
			}
		});
		btnLearnSet.setBounds(23, 20, 124, 36);
		btnLearnSet.setText("Load Learn Set File");
		
		Button btnLoadTestSet = new Button(shell, SWT.FLAT);
		btnLoadTestSet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
		        fd.setText("Open");
		        fd.setFilterPath("C:/");
		        String[] filterExt = { "*.csv",};
		        fd.setFilterExtensions(filterExt);
		        testFileName = fd.open();
		        System.out.println(testFileName);
		        lblTest.setText("File loaded");
				
			}
		});
		btnLoadTestSet.setText("Load Test Set File");
		btnLoadTestSet.setBounds(23, 62, 124, 36);
		
		Button btnOutputDirectory = new Button(shell, SWT.FLAT);
		btnOutputDirectory.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				 DirectoryDialog dialog = new DirectoryDialog(shell);
				    dialog.setFilterPath("c:\\"); // Windows specific
				    outputDirectory=dialog.open();
				    System.out.println(outputDirectory);
				    lblChooseOutputDirectory.setText("Directory chosen");
			}
		});
		btnOutputDirectory.setText("Output Directory");
		btnOutputDirectory.setBounds(23, 104, 124, 36);
		

		
		Menu menu = new Menu(shell);
		shell.setMenu(menu);
		
		Button btnExecute = new Button(shell, SWT.FLAT);
		btnExecute.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Main.main(outputDirectory,learningFileName,testFileName);
				lblExecuteAlgothin.setText("Done");
			}
		});
		btnExecute.setText("Execute");
		btnExecute.setBounds(23, 146, 124, 36);
		

		


	}



//		public static String pickAnEQFile(String[] args) {
//			String locationEQ = null;
//			JFileChooser chooser = new JFileChooser();
//			FileNameExtensionFilter filter = new FileNameExtensionFilter(
//					"XML files", "xml");
//			chooser.setDialogTitle("Select EQ file");
//			chooser.setFileFilter(filter);
//			int returnVal = chooser.showOpenDialog(null);
//			if (returnVal == JFileChooser.APPROVE_OPTION) {
//				locationEQ = chooser.getSelectedFile().getAbsolutePath();
//				System.out.println("You chose to open this EQ file: " + locationEQ);
//			}
//			return locationEQ;
//		}
//
//		public static String pickAnSSHFile(String[] args) {
//			String locationSSH = null;
//			JFileChooser chooser = new JFileChooser();
//			FileNameExtensionFilter filter = new FileNameExtensionFilter(
//					"XML files", "xml");
//			chooser.setDialogTitle("Select SSH file");
//			chooser.setFileFilter(filter);
//			int returnVal = chooser.showOpenDialog(null);
//			if (returnVal == JFileChooser.APPROVE_OPTION) {
//				locationSSH = chooser.getSelectedFile().getAbsolutePath();
//				System.out.println("You chose to open this SSH file: "
//						+ locationSSH);
//			}
//			return locationSSH;
//		}
//	}
}
