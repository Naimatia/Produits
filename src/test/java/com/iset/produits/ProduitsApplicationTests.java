package com.iset.produits;

import java.util.Date;
import java.util.List;

import com.iset.produits.service.ProduitService;
import com.iset.produits.service.ProduitServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.iset.produits.dao.ProduitRepository;
import com.iset.produits.entities.Produit;
import org.springframework.data.domain.Page;

@SpringBootTest
class ProduitsApplicationTests {
    @Autowired
    private ProduitRepository produitRepository;

    @Test
    public void testCreateProduit() {
        Produit prod = new Produit("PC Asus",1500.500,new Date());
        produitRepository.save(prod);
    }
    @Test
    public void testFindProduit()
    {
        Produit p = produitRepository.findById(1L).get();
        System.out.println(p);
    }

    @Test
    public void testUpdateProduit()
    {
        Produit p = produitRepository.findById(1L).get();
        p.setPrixProduit(2000.0);
        produitRepository.save(p);

        System.out.println(p);
    }
    
/*
    @Test
    public void testDeleteProduit()
    {
        produitRepository.deleteById(1L);
    }
*/
    @Test
    public void testFindAllProduits()
    {
        List<Produit> prods = produitRepository.findAll();

        for (Produit p:prods)
            System.out.println(p);

    }
    /*@Test
    public void testFindByNomProduitContains()
    {
        Page<Produit> prods = service.getAllProduitsParPage(0,2);
        System.out.println(prods.getSize());
        System.out.println(prods.getTotalElements());

        System.out.println(prods.getTotalPages());
        prods.getContent().forEach(p -> {System.out.println(p.toString());});
    }
*/
}
