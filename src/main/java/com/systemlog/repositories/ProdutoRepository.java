package com.systemlog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.systemlog.domain.Categoria;
import com.systemlog.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
//	@Query("select distinct obj from Produto obj inner join obj.categorias cat where obj.nome like %:nome% and cat in :categorias")
//	Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageableRequest);

//	Caso seja acrescentada a anotação query ela subscreve a sintaxe
//	@Query("select distinct obj from Produto obj inner join obj.categorias cat where obj.nome like %:nome% and cat in :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageableRequest);

	
	
}
