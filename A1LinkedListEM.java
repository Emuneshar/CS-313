/**
 *  * Student: Eshwar Muneshar
 *   */
public class A1LinkedListEM{
   	public static void main(String argc[]){
		LinkedList<Integer> sl = new LinkedList<>();
		PolynomialLinkedList sum = new PolynomialLinkedList();
		PolynomialLinkedList prod = new PolynomialLinkedList();

		for (int i = 1000; i > 0; i-=3) sl.add(i);

		System.out.println("Mid Element is " + sl.midElement());



		try {
			sl.insert(111, sl.getNode(50), sl.getNode(51));
			if (sl.detectLoop()) System.out.println("Loop!");
			else System.out.println("No loop.");


			sl.insert(123, sl.getNode(51), sl.getNode(50));
			if (sl.detectLoop()) System.out.println("Loop!");
			else System.out.println("No loop.");
		}
		catch(Exception e){
			e.printStackTrace();
		}


		PolynomialLinkedList p1, p2, p3, p4;
		p1 = new PolynomialLinkedList(2,3);
		p2 = new PolynomialLinkedList(3,2);
		p3 = p1.add(p2);
		p1 = new PolynomialLinkedList(3,2);
		p2 = new PolynomialLinkedList(1,0);
		p4 = p1.add(p2);
		sum = p3.add(p4);
		prod = p3.multiply(p4);
		p3.print();
		p4.print();
		sum.print();
		//System.out.println();
		prod.print();

	}
}

class LinkedList<E>{
	private static class Node<E>{
		private E element;
		private Node<E> next;
		public Node(E e, Node<E> n){
			element = e;
			next = n;
		}
		public E getElement(){
			return element;
		}
		public Node<E> getNext(){
			return next;
		}
		public void setElement(E e){
			element = e;
		}
		public void setNext(Node<E> n){
			next = n;
		}
	}
	private Node<E> head;
	public LinkedList(){
		head = null;
	}
	public void add(E e){
		Node<E> temp = new Node<>(e, head);
		head = temp;
	}
	public void insert(E e, Node<E> p, Node<E> n){
		p.setNext(new Node<>(e, n));
	}
	public Node<E> getNode(int i) throws Exception{
		Node<E> temp = head;
		while (i > 0){
			if (temp == null) throw new Exception("Out of bound");
			temp = temp.getNext();
			i--;
		}
		return temp;
	}
	public E midElement(){
		Node <E> tortoise = this.head;
		Node <E> hare = this.head;
		if(this.head != null) {
			while(hare != null && tortoise != null ) {
				hare = hare.getNext().getNext();
				tortoise = tortoise.getNext();
			}
		}

		return tortoise.getElement();
	}
	public boolean detectLoop(){
		Node <E> tortise = this.head;
		Node <E> hare = this.head;
		do{
	        if (hare.getNext() == null || hare.getNext().getNext() == null){
	            return false;
	        }

	        hare = hare.getNext().getNext();
	        tortise = tortise.getNext();
	    }
		while (hare != tortise);

	    return true;
	}
}


class PolynomialLinkedList{
	private static class PNode{
		private int coe;
		private int exp;
		private PNode next;
		public PNode(int c, int e){
			this(c, e, null);
		}
		public PNode(int c, int e, PNode n){
			coe = c;
			exp = e;
			next = n;
		}
		public void setCoe(int c){ coe = c;}
		public void setExp(int e){ exp = e;}
		public void setNext(PNode n){ next = n;}
		public int getCoe(){ return coe;}
		public int getExp(){ return exp;}
		public PNode getNext(){ return next;}
	}
	private PNode first;
	private PNode last;
	    public PolynomialLinkedList(){
			first = last = null;
		}
		public PolynomialLinkedList(int c, int e){
			PNode tempn = new PNode(c, e, null);
			first = last = tempn;
		}

		public void print(){
			if (first == null){
				System.out.println();
				return;
			}
			PNode temp = first;
			String ans = "";
			while (temp != null){
				if (temp.getCoe() > 0) {
					if (temp != first) ans = ans + " + ";
					ans = ans + temp.getCoe();
				}
				else if (temp.getCoe() < 0) ans = ans + " - " + temp.getCoe() * -1;
				if (temp.getExp() != 0){
					ans = ans + "X^" + temp.getExp();
				}
				temp = temp.getNext();
			}
			System.out.println(ans);
		}
		public PolynomialLinkedList add(PolynomialLinkedList s){
			PolynomialLinkedList sum = new PolynomialLinkedList();
			PolynomialLinkedList a = this;
		        PNode temp1 = a.first;
		        PNode temp2 = s.first;
		        while (temp1 != null || temp2 != null) {
		            PNode placeHolder = null;
		            if(temp1 == null){
		            	placeHolder = new PNode(temp2.coe, temp2.exp);
		            	temp2 = temp2.next;
		            }
		            else if(temp2 == null){
		            	placeHolder = new PNode(temp1.coe, temp1.exp);
		            	temp1 = temp1.next;
		            }
		            else if(temp1.exp > temp2.exp){
		            	placeHolder = new PNode(temp1.coe, temp1.exp);
		            	temp1 = temp1.next;
		            }
		            else if(temp1.exp < temp2.exp){
		            	placeHolder = new PNode(temp2.coe, temp2.exp);
		            	temp2 = temp2.next;
		            }

		            else{
		                int coef = temp1.coe + temp2.coe;
		                int exp  = temp1.exp;
		                temp1 = temp1.next;
		                temp2 = temp2.next;
		                if (coef == 0) continue;
		                placeHolder = new PNode(coef, exp);
		            }
		            if(sum.first == null) {
		            	sum.first=sum.last=placeHolder;
		            }
		            else {
		            	sum.last.next=placeHolder;
		            	sum.last=sum.last.next;
		            }
		         }

			return sum;
		}

		public PolynomialLinkedList multiply(PolynomialLinkedList s){
			PolynomialLinkedList temp6 = this;
			PolynomialLinkedList product = new PolynomialLinkedList();

			for (PNode term1 = temp6.first; term1!= null; term1 = term1.next) {
				PolynomialLinkedList temp = new PolynomialLinkedList();

				for (PNode term2 = s.first; term2!= null; term2 = term2.next) {
			    	if(temp.first == null) {
			    		temp.first = temp.last = new PNode(term1.coe * term2.coe, term1.exp + term2.exp);

			    	}
			    	else {
			    		temp.first.next = new PNode(term1.coe * term2.coe, term1.exp + term2.exp);
			    	}

			    }
			    product = product.add(temp);
			 }

			 return product;
		}

}
