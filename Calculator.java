import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
public class Calculator implements ActionListener{
   
   
   JFrame frame = new JFrame();
   
   JPanel main = new JPanel(new BorderLayout(5,5));
   JPanel buttonsContainer = new JPanel(new GridLayout(5,4,5,5));
   JTextField txtField = new JTextField("0.0");
   
   String[][] buttons = {
                         {"(",")",".","<-"},
                         {"1","2","3","*"},
                         {"4","5","6","÷"},
                         {"7","8","9","+"},
                         {"AC","0","=","-"},
                        };
   boolean hasEqual = false;                    
   String operators = "÷/*+-()";
   String specials = "AC=<-.";
   StringBuffer text = new StringBuffer();
   InfixToPostfix itf = new InfixToPostfix();
   public Calculator(){
      setTextResult();
      setMainContainer();
      setButtons();
      frame.setLayout(new BorderLayout());
      frame.add(main, BorderLayout.CENTER);
      frame.setResizable(false);
      frame.setSize(400,500);
      frame.setLocationRelativeTo(null);
      frame.setTitle("Allan");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
   
   public void setTextResult(){
      txtField.setFont(new Font("Arial", Font.PLAIN, 30));
      txtField.setForeground(Color.WHITE);
      txtField.setMargin(new Insets(10,10,10,10));
      txtField.setBorder(new LineBorder(Color.BLACK, 20));
      txtField.setHorizontalAlignment(JTextField.RIGHT);
      txtField.setEditable(false);
      txtField.setBackground(Color.BLACK);
   }
   public void setMainContainer(){
      
      main.setBorder(new EmptyBorder(10,10,10,10));
      main.setBackground(Color.BLACK);
      main.add(txtField, BorderLayout.NORTH);
      main.add(buttonsContainer, BorderLayout.CENTER);
   }
   public void setButtons(){
   
      for(int i = 0; i < buttons.length; i++){
         for(int j=0; j < buttons[i].length; j++){
            
            JButton button = new JButton(buttons[i][j]);
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setFont(new Font("Arial",Font.BOLD, 15));
            button.setForeground(Color.WHITE);
            String color = "#212121";
            if(specials.contains(buttons[i][j])){
               color = "#E6EFE9";
               button.setForeground(Color.BLACK);
            }
            else if(operators.contains(buttons[i][j]))
               color = "#303030";
            button.setBackground(Color.decode(color));
            
            buttonsContainer.add(button);
            button.addActionListener(this);
         }
      }
      buttonsContainer.setBackground(Color.BLACK);
      buttonsContainer.setBorder(BorderFactory.createEmptyBorder());
   }
   
   public boolean isValidInput(String action){
      String field =  txtField.getText();

      if((operators.contains(action) || action.equals("=")) && (operators.indexOf(field.charAt(field.length() -1 )) > -1 || field.equals("0.0")))
         return false;
      return true;
   }  
   @Override
   public void actionPerformed(ActionEvent e){
      String action = e.getActionCommand();
      if(isValidInput(action) && !action.equals("<-")){
      text.append(action);
      
      if(hasEqual && !operators.contains(action))
         text = new StringBuffer(action);
      hasEqual = false;
            
      String result = text.toString();
      switch(action){
         case "AC":
            result = "0.0";
            text = new StringBuffer();
            break;
         case "=":
            try {
            result = itf.calculate(itf.convert(txtField.getText().replace("÷","/")));
            text = new StringBuffer(result);
            hasEqual = true;
            }catch(Exception b){
               result = "Invalid Expression.";
               text = new StringBuffer();
            }
            
      }
      txtField.setText(result);
      
     }else if(action.equals("<-") && text.length() != 0){
         text.deleteCharAt(text.length()-1);
         txtField.setText(text.toString());
         }
   }
   public static void main(String... args){
   
      new Calculator();
   }
}
