package br.fatec.b3comandas.repository.custom;

import java.util.ArrayList;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtil {
    
    public static <E> PageImpl<E> create (Pageable pageable, ArrayList<E> list) {

        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size()
                ? list.size() : (start + pageable.getPageSize());

                //System.out.println("start: " + start);
                //System.out.println("end: " + end);
                
                //evitar bug na interface: se estiver na última página e os itens por página aumentar, reseta para a primeira página
                if (start > end) {
                    start = 0;
                    end = pageable.getPageSize() > list.size() ? list.size() : pageable.getPageSize();
                    pageable = PageRequest.of(start, end);
                }

        return new PageImpl<>(list.subList(start, end), pageable, list.size());


    }
}
