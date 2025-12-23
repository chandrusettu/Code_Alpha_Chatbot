import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Date;
import java.util.regex.Pattern;

/* =========================
   MAIN GUI CLASS
   ========================= */
public class AIChatbot extends JFrame {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private BotEngine bot;

    public AIChatbot() {
        bot = new BotEngine();
        initUI();
    }

    private void initUI() {
        setTitle("Artificial Intelligence Chatbot");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        sendButton = new JButton("Send");

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(inputField, BorderLayout.CENTER);
        bottom.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> send());
        inputField.addActionListener(e -> send());

        addMessage("Bot", "Hello! I am an AI Chatbot 🤖");
    }

    private void send() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            addMessage("You", text);
            inputField.setText("");
            addMessage("Bot", bot.reply(text));
        }
    }

    private void addMessage(String sender, String msg) {
        chatArea.append(sender + ": " + msg + "\n\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AIChatbot().setVisible(true));
    }
}

/* =========================
   BOT ENGINE
   ========================= */
class BotEngine {

    private HashMap<String, ArrayList<String>> knowledgeBase = new HashMap<>();
    private Random random = new Random();

    public BotEngine() {
        trainBot();
    }

    private void trainBot() {

        ArrayList<String> greeting = new ArrayList<>();
        greeting.add("Hello!");
        greeting.add("Hi there!");
        greeting.add("Hey 👋");
        knowledgeBase.put("greeting", greeting);

        ArrayList<String> name = new ArrayList<>();
        name.add("I am an Artificial Intelligence Chatbot.");
        name.add("You can call me AI Bot.");
        knowledgeBase.put("name", name);

        ArrayList<String> ai = new ArrayList<>();
        ai.add("Artificial Intelligence enables machines to think.");
        ai.add("AI mimics human intelligence.");
        knowledgeBase.put("ai", ai);

        ArrayList<String> java = new ArrayList<>();
        java.add("Java is an object-oriented programming language.");
        java.add("Java is widely used in software development.");
        knowledgeBase.put("java", java);

        ArrayList<String> help = new ArrayList<>();
        help.add("You can ask me about AI, Java, or time.");
        help.add("I'm here to help you 😊");
        knowledgeBase.put("help", help);

        ArrayList<String> time = new ArrayList<>();
        time.add("Current time: " + new Date());
        knowledgeBase.put("time", time);

        ArrayList<String> bye = new ArrayList<>();
        bye.add("Goodbye!");
        bye.add("See you soon!");
        knowledgeBase.put("bye", bye);

        ArrayList<String> def = new ArrayList<>();
        def.add("Sorry, I didn't understand that.");
        def.add("Can you rephrase?");
        knowledgeBase.put("default", def);
    }

    public String reply(String input) {

        String text = input.toLowerCase();

        if (match(text, "hello|hi|hey"))
            return randomReply("greeting");

        if (match(text, "your name|who are you"))
            return randomReply("name");

        if (match(text, "ai|artificial"))
            return randomReply("ai");

        if (match(text, "java"))
            return randomReply("java");

        if (match(text, "help"))
            return randomReply("help");

        if (match(text, "time|date"))
            return randomReply("time");

        if (match(text, "bye|exit"))
            return randomReply("bye");

        return randomReply("default");
    }

    private boolean match(String text, String pattern) {
        return Pattern.compile(pattern).matcher(text).find();
    }

    private String randomReply(String key) {

        if (!knowledgeBase.containsKey(key)) {
            key = "default";
        }

        ArrayList<String> list = knowledgeBase.get(key);
        return list.get(random.nextInt(list.size()));
    }
}