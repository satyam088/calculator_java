import java.util.*;

public class calculator {




// HELPING function for postfix

    public static boolean popUntil(char ch,char op){
        if(ch==')' && op=='(') return true;
        if(ch=='}' && op=='{') return true;
        if(ch==']' && op=='[') return true;
        return false;
    }


    // convert to postfix   {HALF COMPLETED FUNC}

    public static void postfix(String a){
        Stack<Character> operator = new Stack<>();
        String ns = "";
        HashMap<Character, Integer> order = new HashMap<>();

        order.put('^',3);
        order.put('*',2);
        order.put('/',2);
        order.put('%',2);
        order.put('+',1);
        order.put('-',1);

        HashSet<Character> open = new HashSet<>();
        open.add('('); open.add('{'); open.add('[');
        HashSet<Character> close = new HashSet<>();
        close.add(')'); close.add('}'); close.add(']');
        for(int i = 0 ; i<a.length();i++){
            char ch = a.charAt(i);
            if(ch==' ') continue ;
            if(ch>='0'&& ch<='9'){
                ns+=a.charAt(i);
            }else if(ch=='('||ch=='{'||ch=='['){
                operator.push(ch);
            }else if(ch==')'||ch=='}'||ch==']'){
                while(!operator.isEmpty()){
                    if(popUntil(ch , operator.peek())){
                        operator.pop();
                        break;
                    }else{
                    char temp = operator.pop();
                    ns+=temp;
                } 
            } 
            }else{
                if(!order.containsKey(ch)) continue;
                while(!operator.isEmpty() && !open.contains(operator.peek()) && (order.get(operator.peek())>=order.get(ch))){
                   ns+=operator.pop();
                }
                operator.push(ch);
            }
        }
        while(!operator.isEmpty()){
            char p = operator.pop();
            if(order.containsKey(p)){
                ns+=p;
            }
        }
        System.out.println(ns);
    }




    // Function for cheking valid paranthesis

    public static boolean valid(String a){
        Stack<Character> para = new Stack<>();
        HashSet<Character> open = new HashSet<>();
        open.add('('); open.add('{'); open.add('[');
        HashSet<Character> close = new HashSet<>();
        close.add(')'); close.add('}'); close.add(']');
        for(int i = 0 ; i<a.length();i++){
            char ch = a.charAt(i);
            if(!open.contains(ch) && !close.contains(ch)) continue ;
            if(open.contains(ch)){
                para.push(ch);
            }else if(!para.isEmpty() &&close.contains(ch)){
                if(ch==')' && (para.peek()=='(')){
                    para.pop();
                }else if(ch=='}' && (para.peek()=='{')){
                    para.pop();
                }else if(ch==']' && (para.peek()=='[')){
                    para.pop();
                }else{
                    para.push(ch);
                }
            }
        }
        if(para.isEmpty())
            return true;
        else
            return false;
        
    }




    // CRUCIAL FUNCTION for cheking validity of the INPUT Expression

    public static boolean checkExp(String a ){
    


        return false;
    }




    // MAIN function for testing purpose only 
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();
        valid(a);

        sc.close();
    }
}