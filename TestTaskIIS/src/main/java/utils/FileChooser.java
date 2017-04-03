package utils;

import javax.swing.*;

public class FileChooser extends JFrame{
        /** выбор файла при помощи JFrame*/
        public String ChooseFile()
        {
            setBounds(0,0,500,500);
            JFileChooser dialog = new JFileChooser();
            dialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            dialog.showOpenDialog(this);
            String filePath =dialog.getSelectedFile().getAbsolutePath();
            return filePath;
        }
}
