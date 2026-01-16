import java.util.*;

public class calculator {

    // HELPING function for postfix

    public static boolean popUntil(char ch,char op){
        if(ch==')' && op=='(') return true;
        if(ch=='}' && op=='{') return true;
        if(ch==']' && op=='[') return true;
        return false;
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

    public static void evaluate(String a ){
            if(!valid(a)){
                System.out.println("Expression is Invalid");
                return;
            }
            solve(a);
 
        return;
    }

    // Most Imp function who actualyy evaluate expression
    
    public static void solve(String exp){

        HashMap<Integer,Double> numbers = new HashMap<>();
        HashMap<Integer,Character> operators = new HashMap<>();
        double currNum = 0;
        int flag = 0;
        HashSet<Character>ops = new HashSet<>(Arrays.asList('+','-','*','^','/','%'));
        int idx_track = 0;
        for(int i = 0 ; i<exp.length();i++){
            char e = exp.charAt(i);
            if(e>='0'&&e<='9'){
                currNum=currNum*10+(e-'0');
                // System.out.println(currNum+" "+i);
                if(i==exp.length()-1){
                    numbers.put(idx_track++,currNum);
                    currNum=0;
                }
                flag=1;
            }else if(ops.contains(e)){
                if(flag==1){
                    // System.out.println(currNum);
                    numbers.put(idx_track++,currNum);
                    flag = 0;
                    currNum=0;
                }
                operators.put(idx_track++,e);
            }else{
                if(e=='('||e=='{'||e=='['){
                    if(flag==1){
                        numbers.put(idx_track++,currNum);
                        currNum=0;
                        operators.put(idx_track++,'*');
                    }
                    operators.put(idx_track++,e);
                }else if(e==')'||e=='}'||e==']'){
                    if(flag!=2){
                        numbers.put(idx_track++,currNum);
                        currNum=0;
                        flag = 2;
                    }
                    operators.put(idx_track++,e);
                }
            }
        }
        System.out.println("Entered Expression is : ");
        for(int i = 0 ; i<idx_track;i++){
            if(numbers.getOrDefault(i, 0.0)!=0.0){
                System.out.print(numbers.get(i)+" ");
            }else{
                System.out.print(operators.get(i)+" ");
            }
        }

        System.out.println();

        int numCount=0,opsCount=0;
        HashMap<Integer,Double> num = new HashMap<>();
        HashMap<Integer,Character> op = new HashMap<>();
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

        Stack<Character> st = new Stack<>();
        int track = 0;
        for(int i = 0 ; i<idx_track;i++){
            if(numbers.getOrDefault(i,0.0)!=0.0){
                num.put(track++,numbers.get(i));
                numCount++;
            }else{
                if(order.containsKey(operators.get(i))){
                    opsCount++;
                }
                if(st.isEmpty()){
                    st.push(operators.get(i));
                    // continue;
                }else{
                    if(open.contains(operators.get(i))){
                        st.push(operators.get(i));
                        // continue;
                    }else if(close.contains(operators.get(i))){
                        while(!st.isEmpty()){
                             if(popUntil(operators.get(i), st.peek())){
                                st.pop();
                                break;
                            }else{
                                op.put(track++,st.pop());
                            }
                        }
                    }else{
                        if(!order.containsKey(operators.get(i))) continue;
                        while(!st.isEmpty() && !open.contains(st.peek()) && (order.get(st.peek())>=order.get(operators.get(i)))){

                            op.put(track++,st.pop());
                        }
                        st.push(operators.get(i));
                    }
                }
            }
        }
        while(!st.isEmpty()){
            char p = st.pop();
            if(order.containsKey(p)){
                op.put(track++,p);
            }
        }

        if(numCount!=opsCount+1){
            System.out.println("Expression is Invalid");
            return;
        }
        System.out.println("Post FIx converted Expersssion : ");

        for(int i = 0 ; i<track;i++){
            if(num.getOrDefault(i,0.0)!=0.0){
                System.out.print(num.get(i)+" ");
            }else{
                System.out.print(op.get(i)+" ");
            }
        }
        System.out.println();

        Stack<Double>result = new Stack<>();
        for(int i = 0 ; i<track;i++){
            if(num.getOrDefault(i,0.0)!=0.0){
                result.push(num.get(i));
            }else{
                char oper = op.get(i);
                double b = result.pop();
                double a = result.pop();
                double res = 0;
                if(oper=='+'){
                    res = a+b;
                }else if(oper=='-'){
                    res = a-b;             
                }else if(oper=='*'){
                    res = a*b;             
                }else if(oper=='/'){
                    try{
                        res = a/b;
                    }catch(Exception e){
                        System.out.println("Error occured can'nt divide by 0 ");
                        return;
                    }            
                }else if(oper=='%'){
                    try{
                        res = a%b;
                    }catch(Exception e){
                        System.out.println("Error occured can'nt divide by 0 ");
                        return;
                    }            
                }else if(oper=='^'){
                    res = Math.pow(a,b);
                }
                result.push(res);
            }
        }
        System.out.println("Final Answer : "+result.pop());
        return;
    }

    // MAIN function for testing purpose only 
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();
        // postfix( a);
        evaluate(a);
        sc.close();
    }
}