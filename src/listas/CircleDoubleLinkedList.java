/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listas;

import Interface.List;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 *
 * @author Edgar Alvarado
 */
public class CircleDoubleLinkedList<E> implements List<E>{
    private Node<E> last;
    private int effective;
    
    public Node<E> getFirstNode(){
        return last.getNext();
    }
    
    public Node<E> getLastNode(){
        return last;
    }
    
    public void setLast(int index){
        if(index>size()||index<0||isEmpty()){
            throw new NoSuchElementException();
        }
        Node<E> nodo=last.getNext();
        int cont=0;
        do{
            if(index==cont){
                last=nodo;
                break;
            }
            nodo=nodo.getNext();
            cont++;
        }while(nodo!=last.getNext());
    }
    
    public void clear(){
        last=null;
        effective=0;
    }
    
    @Override
    public boolean addFirst(E element) {
        Node<E> nodo=new Node<>(element);
        if(element==null){
            return false;
        }else if(size()==0){
            last=nodo;
            last.setNext(last);
            last.setPrevious(last);
        }else{
            nodo.setNext(last.getNext());
            last.getNext().setPrevious(nodo);
            last.setNext(nodo);
            last.getNext().setPrevious(last);
        }
        effective++;
        return true;
    }

    @Override
    public boolean addLast(E element) {
        Node<E> nodo=new Node<>(element);
        if(element==null){
            return false;
        }else if(size()==0){
            last=nodo;
            last.setNext(last);
            last.setPrevious(last);
        }else{
            nodo.setNext(last.getNext());
            last.getNext().setPrevious(nodo);
            last.setNext(nodo);
            nodo.setPrevious(last);
            last=nodo;
        }
        effective++;
        return true;
    }

    @Override
    public boolean removeFirst() {
        if(isEmpty()){
            return false;
        }
        last.getNext().getNext().setPrevious(last);
        last.getNext().setPrevious(null);
        last.setNext(last.getNext().getNext());
        effective--;
        return true;
    }

    @Override
    public boolean removeLast() {
        if(isEmpty()){
            return false;
        }
        int buf=0;
        for(Node<E> n=last.getNext();n!=last;n=n.getNext()){
            if(buf==size()-2){
                n.setNext(last.getNext());
                last.getNext().setPrevious(n);
                last.setNext(null);
                last.setPrevious(null);
                last=n;
            }
            buf++;
        }
        effective--;
        return true;
    }

    @Override
    public E getFirst() {
        return last.getNext().getContent();
    }

    @Override
    public E getLast() {
        return last.getContent();
    }

    @Override
    public boolean insert(int index, E element) {
        if(index>=size()||index<0){
            return false;
        }else if(index==0){
            addFirst(element);
        }else if(index==size()-1){
            addLast(element);
        }else{
            Node<E> nodo = new Node<>(element);
            int buf=0;
            for(Node<E>n=last.getNext();n!=last;n=n.getNext()){
                if(buf==index-1){
                    nodo.setNext(n.getNext());
                    n.getNext().setPrevious(nodo);
                    n.setNext(nodo);
                    nodo.setPrevious(n);
                }
                buf++;
            }
            effective++;
        }
        return true;
    }

    @Override
    public boolean contains(E element) {
        if(element==null||isEmpty()){
            return false;
        }
        Node<E> nodo=last.getNext();
        do{
            if(element==nodo.getContent()){
                return true;
            }
            nodo=nodo.getNext();
        }while(nodo!=last.getNext());
        return false;
    }

    @Override
    public E get(int index) {
        E value=null;
        if(index>size()||index<0||isEmpty()){
            return value;
        }
        Node<E> nodo=last.getNext();
        int cont=0;
        do{
            if(index==cont){
                value=nodo.getContent();
                break;
            }
            nodo=nodo.getNext();
            cont++;
        }while(nodo!=last.getNext());
        return value;
    }

    @Override
    public int indexOf(E element) {
        int value=-1;
        if(isEmpty()|| element==null){
            return value;
        }
        Node<E> nodo=last.getNext();
        int cont=0;
        do{
            if(element==nodo.getContent()){
                value=cont;
                break;
            }
            nodo=nodo.getNext();
            cont++;
        }while(nodo!=last.getNext());
        return value;
    }

    @Override
    public boolean isEmpty() {
        if(effective==0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public E remove(int index) {
        E value=null;
        if(index>=size()||index<0||isEmpty()){
            return value;
        }else if(index==0){
            value=last.getNext().getContent();
            removeFirst();
            return value;
        }else if(index==size()-1){
            value=last.getContent();
            removeLast();
            return value;
        }else{
            int cont=0;
            Node<E> n=last.getNext();
            do{
                if(cont==index-1){
                    n.getNext().getNext().setPrevious(n);
                    n.getNext().setPrevious(null);
                    n.setNext(n.getNext().getNext());
                    effective--;
                    return value;
                }
                n=n.getNext();
                cont++;
            }while(n!=last.getNext());
        }
        return null;
    }

    @Override
    public boolean remove(E element, Comparator<E> cmp) {
        boolean value=false;
        Node<E> nodo=last.getNext();
        do{
            if(cmp.compare(element, nodo.getContent())==0){
                value=true;
                nodo.getPrevious().setNext(nodo.getNext());
                nodo.getNext().setPrevious(nodo.getPrevious());
                nodo.setNext(null);
                nodo.setPrevious(null);
                effective--;
                return value;
            }
            nodo=nodo.getNext();
        }while(nodo!=last.getNext());
        return value;
    }

    @Override
    public E set(int index, E element) {
        E value = null;
        if(index >= size()||index<0||element == null){
            return value;
        }
        Node<E> nodo = last.getNext();
        int cont=0;
        do{
            if(index == cont){
                value = nodo.getContent();
                nodo.setContent(element);
                return value;
            }
            cont++;
            nodo=nodo.getNext();
        }while(nodo!=last.getNext());
        return value;
    }

    @Override
    public int size() {
        return effective;
    }
    
    public ListIterator<E> listIterator(){
        return new ListIterator<E>(){
            Node<E> nodo = last.getNext();
            @Override
            public boolean hasNext() {
                return size()>0;
            }

            @Override
            public E next() {
                if(!hasNext()){
                    throw new NoSuchElementException();
                }
                E value = nodo.getContent();
                nodo = nodo.getNext();
                return value;
            }

            @Override
            public boolean hasPrevious() {
                return size()>0;
            }

            @Override
            public E previous() {
                if(!hasPrevious()){
                    throw new NoSuchElementException();
                }
                E value = nodo.getContent();
                nodo = nodo.getPrevious();
                return value;
            }           

            @Override
            public int nextIndex() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int previousIndex() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void set(E e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void add(E e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>(){
            int posN = 0;
            Node<E> nodoN = last.getPrevious();
            int postP = 0;
            Node<E> nodoP = last;
            UnsupportedOperationException unsuppor = new UnsupportedOperationException();
            @Override
            public boolean hasNext() {
                return posN<size();
            }

            @Override
            public E next() {
                if(!hasNext()){
                    throw new NoSuchElementException();
                }
                E value = nodoN.getContent();
                nodoN = nodoN.getNext();
                posN++;
                return value;
            }
            
        };
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("[");
        switch(size()){
            case 0:
                s.append("]");
                return s.toString();
            case 1:
                s.append(last.getNext().getContent().toString()).append("]");
                return s.toString();
            default: 
                Node<E> nodo = last.getNext();
                do{
                    if(nodo == last){
                        s.append(nodo.getContent().toString()).append("]");
                    }else{
                        s.append(nodo.getContent().toString()).append(",");
                    }
                    nodo = nodo.getNext();
                }while(nodo != last.getNext());
                return s.toString();
        }
    }
    
}
