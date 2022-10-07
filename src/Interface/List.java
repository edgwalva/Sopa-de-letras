/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.Comparator;

/**
 *
 * @author Grupo_12
 */

//Interface List con parametrizacion por tipo
public interface List<E> extends Iterable<E> {
    boolean addFirst(E element);
    boolean addLast(E element);
    boolean removeFirst();
    boolean removeLast();
    E getFirst();
    E getLast();
    boolean insert(int index, E element);
    boolean contains(E element);
    E get(int index);
    int indexOf(E element);
    boolean isEmpty();
    E remove(int index);
    boolean remove(E element, Comparator<E> cmp);
    E set(int index, E element);
    int size();
}
