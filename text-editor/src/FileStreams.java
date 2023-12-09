import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FileStreams
{
    public static void main(String args[])
    {
        FrameWindow frame;
        frame =  new FrameWindow(
                "Java Notepad");
        frame.setVisible(true);
    }
}

class FrameWindow extends Frame
        implements ActionListener, WindowListener
{
    TextArea ta;

    MenuBar mb;

    Menu mFile;
    MenuItem miOpen;
    MenuItem miSave;
    MenuItem miSaveAs;
    MenuItem miExit;

    String szCurrentFilename = "";
    byte buf[];

    public FrameWindow(String szTitle)
    {
        super(szTitle);
        setSize(400, 300);

        mb = new MenuBar();
        mFile = new Menu("File");

        miOpen = new MenuItem("Open...");
        mFile.add(miOpen);

        miSave = new MenuItem("Save");
        mFile.add(miSave);

        miSaveAs = new MenuItem("Save As...");
        mFile.add(miSaveAs);

        mFile.add("-");

        miExit = new MenuItem("Exit");
        mFile.add(miExit);

        mb.add(mFile);

        miOpen.addActionListener(this);
        miSave.addActionListener(this);
        miSaveAs.addActionListener(this);
        miExit.addActionListener(this);

        setMenuBar(mb);

        this.addWindowListener(this);

        ta = new TextArea(10, 30);
        setLayout(new BorderLayout());
        add("Center", ta);

    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource().equals(miOpen))
        {
            FileOpen();
        }

        else if(e.getSource().equals(miSave))
        {
            FileSave();
        }

        else if(e.getSource().equals(miSaveAs))
        {
            FileSaveAs();
        }

        else if(e.getSource().equals(miExit))
        {
            setVisible(false);
            System.exit(0);
        }
    }

    public void windowClosing(WindowEvent e)
    {
        setVisible(false);
        System.exit(0);
    }

    public void windowOpened(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}

    void FileOpen()
    {
        FileDialog fdlg;
        FileInputStream is = null;

        fdlg = new FileDialog(this, "Open file",
                FileDialog.LOAD);
        fdlg.show();

        szCurrentFilename = fdlg.getDirectory() +
                fdlg.getFile();

        setTitle("Java Notepad" + " - " +
                szCurrentFilename);

        try
        {
            is = new FileInputStream(
                    szCurrentFilename);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.toString());
        }
        catch (SecurityException ex)
        {
            System.out.println(ex.toString());
        }

        try
        {
            buf = new byte[is.available()];
            is.read(buf);
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }

        ta.selectAll();
        ta.replaceRange(
                "", 0, ta.getSelectionEnd());

        String szStr = new String(buf);
        StringTokenizer st;

        st = new StringTokenizer(szStr, "\r\n");

        while(st.hasMoreElements())
        {
            szStr = new String(
                    (String)st.nextElement());
            ta.append(szStr + "\r\n");
        }

        try
        {
            is.close();
        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
    }

    void FileSaveAs()
    {
        FileDialog fdlg;
        fdlg = new FileDialog(
                this, "Save file as...",
                FileDialog.SAVE);
        fdlg.show();

        if(fdlg.getDirectory() == null ||
                fdlg.getFile() == null)
            return;

        szCurrentFilename = fdlg.getDirectory() +
                fdlg.getFile();

        setTitle("Java Notepad" + " - " +
                szCurrentFilename);

        FileSave();
    }

    void FileSave()
    {
        FileOutputStream os = null;

        String sz = ta.getText();
        buf = sz.getBytes();

        try
        {
            os = new FileOutputStream(
                    szCurrentFilename);
            os.write(buf);
            os.close();

        }
        catch (IOException ex)
        {
            System.out.println(ex.toString());
        }
        catch (SecurityException ex)
        {
            System.out.println(ex.toString());
        }
    }
}