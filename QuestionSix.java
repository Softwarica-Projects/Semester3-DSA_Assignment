import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class QuestionSix extends JFrame {
    private final JProgressBar overallProgressBar;
    private final JTextArea statusArea;
    private final JFileChooser fileChooser;
    private List<Thread> threads;
    private List<File> selectedFiles;
    private boolean isCancelled;

    public QuestionSix() {
        setTitle("File Converter");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton selectFilesButton = new JButton("Select Files");
        JButton startButton = new JButton("Start Conversion");
        JButton cancelButton = new JButton("Cancel");
        overallProgressBar = new JProgressBar(0, 100);
        statusArea = new JTextArea();
        statusArea.setEditable(false);

        JComboBox<String> formatComboBox = new JComboBox<>(new String[]{"PDF to Docx", "Image Resize"});

        panel.add(selectFilesButton);
        panel.add(formatComboBox);
        panel.add(startButton);
        panel.add(cancelButton);
        panel.add(overallProgressBar);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(statusArea), BorderLayout.CENTER);

        fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);

        // Action Listeners
        selectFilesButton.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(QuestionSix.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFiles = List.of(fileChooser.getSelectedFiles());
            }
        });

        startButton.addActionListener(e -> startConversion());

        cancelButton.addActionListener(e -> cancelConversion());
    }

    private void startConversion() {
        if (selectedFiles == null || selectedFiles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No files selected.");
            return;
        }

        isCancelled = false;
        threads = new ArrayList<>();
        overallProgressBar.setValue(0);

        for (File file : selectedFiles) {
            Thread thread = new Thread(new FileConverterTask(file));
            threads.add(thread);
            thread.start();
        }
    }

    private void cancelConversion() {
        isCancelled = true;
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    private class FileConverterTask implements Runnable {
        private final File file;

        public FileConverterTask(File file) {
            this.file = file;
        }

        @Override
        public void run() {
            if (isCancelled) return;

            try {
                // Simulate file conversion
                for (int i = 0; i < 100; i++) {
                    if (Thread.currentThread().isInterrupted() || isCancelled) {
                        return;
                    }
                    Thread.sleep(50); // Simulating work
                    int finalI = i;
                    SwingUtilities.invokeLater(() -> statusArea.append("Processing: " + file.getName() + " - " + finalI + "%\n"));
                }
                SwingUtilities.invokeLater(() -> statusArea.append("Completed: " + file.getName() + "\n"));
            } catch (InterruptedException e) {
                SwingUtilities.invokeLater(() -> statusArea.append("Cancelled: " + file.getName() + "\n"));
            }

            updateOverallProgress();
        }

        private synchronized void updateOverallProgress() {
            SwingUtilities.invokeLater(() -> {
                int completed = (int) threads.stream().filter(Thread::isAlive).count();
                int progress = (int) (((selectedFiles.size() - completed) / (double) selectedFiles.size()) * 100);
                overallProgressBar.setValue(progress);
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuestionSix gui = new QuestionSix();
            gui.setVisible(true);
        });
    }
}
