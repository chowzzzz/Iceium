/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CategoryEntitySessionBeanLocal;
import ejb.session.stateless.ColorEntitySessionBeanLocal;
import ejb.session.stateless.ModelEntitySessionBeanLocal;
import ejb.session.stateless.ProductEntitySessionBeanLocal;
import ejb.session.stateless.SaleEntitySessionBeanLocal;
import ejb.session.stateless.SizeEntitySessionBeanLocal;
import ejb.session.stateless.TagEntitySessionBeanLocal;
import entity.ProductEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import util.enumeration.SizeTypeEnum;
import util.exception.ProductNotFoundException;
import util.formatter.ConsoleLogger;
import util.generator.RandomNumberGenerator;
import util.initialization.ProductData;

/**
 *
 * @author Theodoric
 */
@Startup
@LocalBean
@Singleton
@DependsOn("ProductDetailsInitializationSessionBean")
public class ProductInitializationSessionBean
{

    @EJB(name = "saleEntitySessionBeanLocal")
    private SaleEntitySessionBeanLocal saleEntitySessionBeanLocal;

    @EJB(name = "modelEntitySessionBeanLocal")
    private ModelEntitySessionBeanLocal modelEntitySessionBeanLocal;

    @EJB(name = "colorEntitySessionBeanLocal")
    private ColorEntitySessionBeanLocal colorEntitySessionBeanLocal;

    @EJB(name = "sizeEntitySessionBeanLocal")
    private SizeEntitySessionBeanLocal sizeEntitySessionBeanLocal;

    @EJB(name = "productEntitySessionBeanLocal")
    private ProductEntitySessionBeanLocal productEntitySessionBeanLocal;

    @EJB(name = "tagEntitySessionBeanLocal")
    private TagEntitySessionBeanLocal tagEntitySessionBeanLocal;

    @EJB(name = "categoryEntitySessionBeanLocal")
    private CategoryEntitySessionBeanLocal categoryEntitySessionBeanLocal;

    private ProductData productData;
    private RandomNumberGenerator randomNumberGenerator1;
    private RandomNumberGenerator randomNumberGenerator2;

    public ProductInitializationSessionBean()
    {
    }

    @PostConstruct
    public void postConstruct()
    {
        try
        {
            ConsoleLogger.log("Started");
            productEntitySessionBeanLocal.retrieveProductByProductId(1L);
            ConsoleLogger.log("No Generation");
        }
        catch (ProductNotFoundException ex)
        {
            productData = new ProductData();
            randomNumberGenerator1 = new RandomNumberGenerator(1, tagEntitySessionBeanLocal.retrieveAllTags().size());
            randomNumberGenerator2 = new RandomNumberGenerator(1, saleEntitySessionBeanLocal.retrieveAllSales().size());
            initializeData();
            ConsoleLogger.log("Completed");
        }
    }

    /**
     * Product Creation Method Start
     */
    private void initializeData()
    {
        try
        {
            createProducts();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private void createProducts() throws Exception
    {
        int quantityOnHand = 100;
        int reorderQuantity = 10;

        /**
         * Figure Skates
         */
        createProduct("Edea Piano Ice Skates", 1149.99, "Edea", "Piano", "Figure Skates", 6.5, 8.0, SizeTypeEnum.NORMAL, "White", quantityOnHand, reorderQuantity, "edea-piano__97802.1605009747.462.464.jpg");

        /**
         * Senior Ice Hockey Skates
         */
        createProduct("Bauer Supreme S29 Senior Ice Hockey Skates", 309.99, "Bauer", "Supreme", "Senior Ice Hockey Skates", 6.0, 11.0, SizeTypeEnum.NORMAL, "White", quantityOnHand, reorderQuantity, "bauer-hockey-skates-supreme-s29-sr.jpg");
        createProduct("Bauer Supreme S35 Senior Ice Hockey Skates", 219.99, "Bauer", "Supreme", "Senior Ice Hockey Skates", 7.0, 12.0, SizeTypeEnum.NORMAL, "Yellow", quantityOnHand, reorderQuantity, "bauer-hockey-skates-supreme-s35-sr.jpg");
        createProduct("Bauer Supreme S36 Senior Ice Hockey Skates", 279.99, "Bauer", "Supreme", "Senior Ice Hockey Skates", 7.0, 10.0, SizeTypeEnum.NORMAL, "Black", quantityOnHand, reorderQuantity, "bauer-hockey-skates-supreme-s36-sr.jpg");
        createProduct("Bauer Supreme S37 Senior Ice Hockey Skates", 309.99, "Bauer", "Supreme", "Senior Ice Hockey Skates", 7.0, 12.0, SizeTypeEnum.NORMAL, "Yellow", quantityOnHand, reorderQuantity, "bauer-hockey-skates-supreme-s37-sr.jpg");
        createProduct("Bauer Supreme UltraSonic Senior Ice Hockey Skates", 1299.99, "Bauer", "Supreme", "Senior Ice Hockey Skates", 7.0, 11.5, SizeTypeEnum.NORMAL, "Yellow", quantityOnHand, reorderQuantity, "bauer-hockey-skates-supreme-ultrasonic-sr.jpg");
        createProduct("Bauer Supreme 2S Senior Ice Hockey Skates", 809.99, "Bauer", "Supreme", "Senior Ice Hockey Skates", 6.0, 12.0, SizeTypeEnum.NORMAL, "Yellow", quantityOnHand, reorderQuantity, "bauer-hockey-skates-supreme-2s-sr.jpg");
        createProduct("Bauer Supreme 3S Senior Ice Hockey Skates", 479.99, "Bauer", "Supreme", "Senior Ice Hockey Skates", 7.0, 12.0, SizeTypeEnum.NORMAL, "Yellow", quantityOnHand, reorderQuantity, "bauer-hockey-skates-supreme-3s-sr.jpg");
        createProduct("Bauer Vapor 2x Senior Ice Hockey Skates", 849.99, "Bauer", "Vapor", "Senior Ice Hockey Skates", 6.0, 12.0, SizeTypeEnum.NORMAL, "Red", quantityOnHand, reorderQuantity, "bauer-ice-hockey-skates-vapor-2x-sr-inset11.jpg");
        createProduct("Bauer Vapor 2x Pro Senior Ice Hockey Skates", 1269.99, "Bauer", "Vapor", "Senior Ice Hockey Skates", 6.0, 11.0, SizeTypeEnum.NORMAL, "Black", quantityOnHand, reorderQuantity, "bauer-ice-hockey-skates-vapor-2x-pro-sr-inset11.jpg");
        createProduct("Bauer Vapor X2.5 Senior Ice Hockey Skates", 201.99, "Bauer", "Vapor", "Senior Ice Hockey Skates", 6.0, 12.0, SizeTypeEnum.NORMAL, "Red", quantityOnHand, reorderQuantity, "bauer-ice-hockey-skates-vapor-x2-5-sr-inset10.jpg");
        createProduct("Bauer Vapor X2.7 Senior Ice Hockey Skates", 289.99, "Bauer", "Vapor", "Senior Ice Hockey Skates", 6.0, 12.0, SizeTypeEnum.NORMAL, "Red", quantityOnHand, reorderQuantity, "bauer-ice-hockey-skates-vapor-x2-7-sr-inset10.jpg");
        createProduct("Bauer Vapor X2.9 Senior Ice Hockey Skates", 479.99, "Bauer", "Vapor", "Senior Ice Hockey Skates", 6.0, 12.0, SizeTypeEnum.NORMAL, "Red", quantityOnHand, reorderQuantity, "bauer-ice-hockey-skates-vapor-x2-9-sr-inset10.jpg");
        createProduct("Bauer Vapor X800 Senior Ice Hockey Skates - '17 Model", 739.99, "Bauer", "Vapor", "Senior Ice Hockey Skates", 6.0, 12.0, SizeTypeEnum.NORMAL, "Red", quantityOnHand, reorderQuantity, "bauer-hockey-skates-vapor-x800-17-sr-inset10.jpg");

        createProduct("CCM Super Tacks AS1 Senior Ice Hockey Skates", 1139.99, "CCM", "Tacks", "Senior Ice Hockey Skates", 6.0, 12.0, SizeTypeEnum.NORMAL, "Yellow", quantityOnHand, reorderQuantity, "ccm-hockey-skates-super-tacks-as1-sr.jpg");
        createProduct("CCM Super Tacks AS3 Senior Ice Hockey Skates", 849.99, "CCM", "Tacks", "Senior Ice Hockey Skates", 7.0, 11.5, SizeTypeEnum.NORMAL, "Yellow", quantityOnHand, reorderQuantity, "ccm-hockey-skates-super-tacks-as3-sr.jpg");
        createProduct("CCM Super Tacks AS3 Pro Senior Ice Hockey Skates", 1269.99, "CCM", "Tacks", "Senior Ice Hockey Skates", 6.0, 11.0, SizeTypeEnum.NORMAL, "White", quantityOnHand, reorderQuantity, "ccm-hockey-skates-super-tacks-as3-pro-sr.jpg");
        createProduct("CCM Super Tacks 9380 Senior Ice Hockey Skates", 479.99, "CCM", "Tacks", "Senior Ice Hockey Skates", 7.0, 12.0, SizeTypeEnum.NORMAL, "Yellow", quantityOnHand, reorderQuantity, "ccm-hockey-skates-super-tacks-9380-sr.jpg");
        createProduct("CCM Jetspeed FT470 Senior Ice Hockey Skates", 439.99, "CCM", "Jetspeed", "Senior Ice Hockey Skates", 6.0, 12.0, SizeTypeEnum.NORMAL, "Red", quantityOnHand, reorderQuantity, "ccm-ice-hockey-skates-jetspeed-ft470-sr.jpg");
        createProduct("CCM Jetspeed FT480 Senior Ice Hockey Skates", 669.99, "CCM", "Jetspeed", "Senior Ice Hockey Skates", 6.0, 12.0, SizeTypeEnum.NORMAL, "Red", quantityOnHand, reorderQuantity, "ccm-ice-hockey-skates-jetspeed-ft480-sr-inset10.jpg");
        createProduct("CCM RibCor 78K Senior Ice Hockey Skates", 549.99, "CCM", "RibCor", "Senior Ice Hockey Skates", 6.0, 11.0, SizeTypeEnum.NORMAL, "Black", quantityOnHand, reorderQuantity, "ccm-hockey-skates-78k-sr.jpg");

        createProduct("Graf G755 Pro Senior Ice Hockey Skates", 739.99, "Graf", "G Series", "Senior Ice Hockey Skates", 6.0, 11.0, SizeTypeEnum.NORMAL, "Black", quantityOnHand, reorderQuantity, "graf-hockey-skates-g755-pro-sr.jpg");
        createProduct("Graf PK3900 Senior Ice Hockey Skates", 369.99, "Graf", "PeakSpeed", "Senior Ice Hockey Skates", 6.0, 12.0, SizeTypeEnum.NORMAL, "Black", quantityOnHand, reorderQuantity, "graf-hockey-skates-pk3900-sr.jpg");
        createProduct("Graf PK7900 Senior Ice Hockey Skates", 949.99, "Graf", "PeakSpeed", "Senior Ice Hockey Skates", 6.0, 12.0, SizeTypeEnum.NORMAL, "Black", quantityOnHand, reorderQuantity, "graf-hockey-skates-pk7900-sr.jpg");

        createProduct("True TF7 Senior Ice Hockey Skates", 444.99, "True", "N/A", "Senior Ice Hockey Skates", 6.0, 11.5, SizeTypeEnum.NORMAL, "Blue", quantityOnHand, reorderQuantity, "true-hockey-skates-tf7-sr.jpg");

        createProduct("A&R Bob Training Skates", 29.99, "A&R", "N/A", "Senior Ice Hockey Skates", 6.0, 7.0, SizeTypeEnum.NORMAL, "Black", quantityOnHand, reorderQuantity, "ar-bob-skates.jpg");

        /**
         * Junior Ice Hockey Skates
         */
        createProduct("Bauer Nexus 2N Junior Ice Hockey Skates", 599.99, "Bauer", "Nexus", "Junior Ice Hockey Skates", 3.0, 5.5, SizeTypeEnum.NORMAL, "Blue", quantityOnHand, reorderQuantity, "bauer-hockey-skates-nexus-2n-jr.jpg");
        createProduct("Bauer Supreme S29 Junior Ice Hockey Skates", 269.99, "Bauer", "Supreme", "Junior Ice Hockey Skates", 4.5, 5.0, SizeTypeEnum.NORMAL, "Yellow", quantityOnHand, reorderQuantity, "bauer-hockey-skates-supreme-s29-jr.jpg");
        createProduct("True TF7 Junior Ice Hockey Skates", 269.99, "True", "N/A", "Junior Ice Hockey Skates", 2.5, 5.5, SizeTypeEnum.NORMAL, "Blue", quantityOnHand, reorderQuantity, "true-hockey-skates-tf7-jr.jpg");
        createProduct("Graf PeakSpeed PK3300 Junior Ice Hockey Skates", 279.99, "Graf", "PeakSpeed", "Junior Ice Hockey Skates", 3.0, 5.5, SizeTypeEnum.NORMAL, "Black", quantityOnHand, reorderQuantity, "graf-hockey-skates-peakspeed-pk3300-jr.jpg");
        createProduct("CCM Super Tacks AS3 Pro Intermediate Ice Hockey Skates", 1079.99, "CCM", "Tacks", "Junior Ice Hockey Skates", 4.0, 6.5, SizeTypeEnum.NORMAL, "Yellow", quantityOnHand, reorderQuantity, "ccm-hockey-skates-super-tacks-as3-pro-int.jpg");

        /**
         * Youth Ice Hockey Skates
         */
        createProduct("CCM Super Tacks 9350 Youth Ice Hockey Skates", 74.99, "CCM", "Tacks", "Youth Ice Hockey Skates", 6.0, 13.0, SizeTypeEnum.YOUTH, "Yellow", quantityOnHand, reorderQuantity, "ccm-hockey-skates-super-tacks-9350-yth.jpg");
        createProduct("CCM Jetspeed FT2 Youth Ice Hockey Skates", 249.99, "CCM", "JetSpeed", "Youth Ice Hockey Skates", 8.0, 13.5, SizeTypeEnum.YOUTH, "Red", quantityOnHand, reorderQuantity, "ccm-ice-hockey-skates-jetspeed-ft2-yth-inset10.jpg");
        createProduct("CCM Super Tacks AS1 Youth Ice Hockey Skates", 249.99, "CCM", "Tacks", "Youth Ice Hockey Skates", 8.0, 12.0, SizeTypeEnum.YOUTH, "Yellow", quantityOnHand, reorderQuantity, "ccm-hockey-skates-super-tacks-as1-yth.jpg");
        createProduct("Bauer Vapor X2.7 Youth Ice Hockey Skates", 109.99, "Bauer", "Vapor", "Youth Ice Hockey Skates", 8.0, 13.5, SizeTypeEnum.YOUTH, "Red", quantityOnHand, reorderQuantity, "bauer-ice-hockey-skates-vapor-x2-7-yth-inset10.jpg");
        createProduct("Bauer Supreme 3S Youth Ice Hockey Skates", 139.99, "Bauer", "Supreme", "Youth Ice Hockey Skates", 8.0, 13.5, SizeTypeEnum.YOUTH, "Yellow", quantityOnHand, reorderQuantity, "bauer-hockey-skates-supreme-3s-yth.jpg");

        /**
         * Accessories
         */
        createProduct("NHL Official Black Ice Hockey Puck", 2.99, "Monkeysports", "N/A", "Pucks", 0.0, 0.0, SizeTypeEnum.NONE, "Black", quantityOnHand, reorderQuantity, "NHL_Official_Black_Ice_Hockey_Puck.jpg");
        createProduct("Bauer Standard Ice Hockey Puck", 2.99, "Bauer", "N/A", "Pucks", 0.0, 0.0, SizeTypeEnum.NONE, "Black", quantityOnHand, reorderQuantity, "bauer-hockey-accessories-puck-inset1.jpg");
        createProduct("A&R 4oz. Ice Hockey Mite Puck", 5.49, "A&R", "N/A", "Pucks", 0.0, 0.0, SizeTypeEnum.NONE, "Blue", quantityOnHand, reorderQuantity, "A&R_4oz._Ice_Hockey_Mite_Puck.jpg");
        createProduct("Winnwell Ice Hockey Puck Pail - 18 Pack", 33.99, "Winnwell", "N/A", "Pucks", 0.0, 0.0, SizeTypeEnum.NONE, "Black", quantityOnHand, reorderQuantity, "Winnwell_Ice_Hockey_Puck_Pail_18_Pack.jpg");

        createProduct("Renfrew Clear Shinpad Hockey Tape - 3 Pack", 12.10, "Renfrew", "N/A", "Tapes", 0.0, 0.0, SizeTypeEnum.NONE, "Clear", quantityOnHand, reorderQuantity, "Renfrew_Clear_Shinpad_Hockey_Tape_3_Pack.jpg");
        createProduct("Renfrew Black Cloth Hockey Tape - 3 Pack", 13.50, "Renfrew", "N/A", "Tapes", 0.0, 0.0, SizeTypeEnum.NONE, "Black", quantityOnHand, reorderQuantity, "Renfrew_Black_Cloth_Hockey_Tape_3_Pack.jpg");
        createProduct("Renfrew White Cloth Hockey Tape - 3 Pack", 13.50, "Renfrew", "N/A", "Tapes", 0.0, 0.0, SizeTypeEnum.NONE, "White", quantityOnHand, reorderQuantity, "Renfrew_White_Cloth_Hockey_Tape_3_Pack.jpg");

        createProduct("Winnwell Pro Form 72in. Regulation Hockey Net w/ QuickNet Mesh System", 229.99, "Winnwell", "N/A", "Nets", 0.0, 0.0, SizeTypeEnum.NONE, "Red", quantityOnHand, reorderQuantity, "winnwell-hockey-accessories-72in-net-quicknet.jpg");
        createProduct("Winnwell 60in. Hockey Net w/ QuickNet Mesh System", 134.99, "Winnwell", "N/A", "Nets", 0.0, 0.0, SizeTypeEnum.NONE, "Red", quantityOnHand, reorderQuantity, "winnwell-hockey-accessories-60in-net-quicknet.jpg");
        createProduct("WinnWell 54in. Hockey Net w/ QuikNet Mesh System", 94.99, "Winnwell", "N/A", "Nets", 0.0, 0.0, SizeTypeEnum.NONE, "Red", quantityOnHand, reorderQuantity, "winnwell-hockey-accessories-54in-net-quicknet.jpg");

        createProduct("Elite PRO-X7 Wide Moulded Tip Laces", 6.75, "Elite", "Pro", "Laces", 0.0, 0.0, SizeTypeEnum.NONE, "Red", quantityOnHand, reorderQuantity, "elite-hockey-laces-pro-x7-wide.jpg");
        createProduct("Elite WAXED Molded Tip Laces", 6.45, "Elite", "N/A", "Laces", 0.0, 0.0, SizeTypeEnum.NONE, "Orange", quantityOnHand, reorderQuantity, "elite-waxmolded-tip-laces.jpg");
        createProduct("Elite Pro S700 WAXED Molded Tip Laces", 6.75, "Elite", "Pro", "Laces", 0.0, 0.0, SizeTypeEnum.NONE, "Black", quantityOnHand, reorderQuantity, "elite-hockey-accessories-laces-pro-s700-waxed.jpg");
        createProduct("Elite Pro-Series Premium Wide NON-WAXED Molded Tip Laces", 7.99, "Elite", "Pro", "Laces", 0.0, 0.0, SizeTypeEnum.NONE, "Yellow", quantityOnHand, reorderQuantity, "elite-hockey-lace-pro-series-premium-wide.jpg");

        createProduct("Warrior Alpha QX Grip Senior Hockey Stick", 369.99, "Warrior", "Alpha", "Sticks", 0.0, 0.0, SizeTypeEnum.NONE, "Yellow", quantityOnHand, reorderQuantity, "warrior-hockey-stick-alpha-qx-grip-sr.jpg");
        createProduct("Warrior Covert QRE SuperLight Grip Senior Hockey Stick", 349.99, "Warrior", "Covert", "Sticks", 0.0, 0.0, SizeTypeEnum.NONE, "Red", quantityOnHand, reorderQuantity, "warrior-hockey-stick-covert-qre-sl-grip-sr.jpg");
        createProduct("Warrior Covert QRE5 Grip Senior Hockey Stick", 109.99, "Warrior", "Covert", "Sticks", 0.0, 0.0, SizeTypeEnum.NONE, "Red", quantityOnHand, reorderQuantity, "warrior-hockey-stick-covert-qre5-grip-sr-inset1.jpg");
        createProduct("CCM Super Tacks 9280 Grip Senior Hockey Stick", 149.99, "CCM", "Tacks", "Sticks", 0.0, 0.0, SizeTypeEnum.NONE, "Yellow", quantityOnHand, reorderQuantity, "ccm-hockey-stick-super-tacks-9280-grip-sr.jpg");
        createProduct("Twigz ABS Senior Wood Hockey Stick", 30.99, "Monkeysports", "Twigz", "Sticks", 0.0, 0.0, SizeTypeEnum.NONE, "Lemon Chiffon", quantityOnHand, reorderQuantity, "twigz-hockey-stick-abs-wood-senior.jpg");
    
        createProduct("Bladetech Speed Blade +4.0 Stainless Steel Runner - Pair", 109.99, "Bladetech", "N/A", "Runners", 0.0, 0.0, SizeTypeEnum.NONE, "Silver", quantityOnHand, reorderQuantity, "bladetech-hockey-runner-ccm-speedblade-4-0-stainless-steel-pair.jpg");
        createProduct("Massive Blade LS Edge Classic Replacement Steel - Pair", 89.99, "Massive", "N/A", "Runners", 0.0, 0.0, SizeTypeEnum.NONE, "Silver", quantityOnHand, reorderQuantity, "massive-hockey-runner-ls-edge-classic-pair-inset1.jpg");
        createProduct("Massive Blade LS2 Gold Replacement Runner - Pair", 129.99, "Massive", "N/A", "Runners", 0.0, 0.0, SizeTypeEnum.NONE, "Gold", quantityOnHand, reorderQuantity, "massive-hockey-runner-ls2-gold-pair.jpg");
        createProduct("Massive Blade Speed Blade Gold Replacement Runner - Pair", 129.99, "Massive", "N/A", "Runners", 0.0, 0.0, SizeTypeEnum.NONE, "Gold", quantityOnHand, reorderQuantity, "massive-hockey-runner-sb-gold-pair.jpg");
    
        createProduct("Elite Pro Blade Soakers", 27.99, "Elite", "N/A", "Soakers", 0.0, 0.0, SizeTypeEnum.NONE, "Yellow", quantityOnHand, reorderQuantity, "elite-hockey-accessories-pro-blade-soaker.jpg");
        createProduct("Tuffterrys Blade Covers", 17.99, "Tuffterrys", "N/A", "Soakers", 0.0, 0.0, SizeTypeEnum.NONE, "Black", quantityOnHand, reorderQuantity, "ar-tuffterrys-bladecovers.jpg");
        createProduct("Elite Notorious Pro Ultra Dry Blade Soakers", 28.99, "Elite", "N/A", "Soakers", 0.0, 0.0, SizeTypeEnum.NONE, "Orange", quantityOnHand, reorderQuantity, "elite-skate-accessories-notorious-pro-ultra-dry-soaker.jpg");
    }

    private void createProduct(String name, Double price, String brand, String model, String category, Double sizeLower, Double sizeUpper, SizeTypeEnum sizeTypeEnum, String color, Integer quantityOnHand, Integer reorderQuantity, String imageLink) throws Exception
    {
        ProductEntity newProductEntity = new ProductEntity(name, productData.getDescription(name), BigDecimal.valueOf(price), imageLink);
        Long modelId = modelEntitySessionBeanLocal.retrieveModelByModelNameAndBrandName(model, brand).getModelId();
        Long categoryId = categoryEntitySessionBeanLocal.retrieveCategoryByName(category).getCategoryId();
        Long saleId = (randomNumberGenerator2.getRandomInteger() % 10) == 0 ? null : randomNumberGenerator2.getRandomInteger().longValue();

        List<Long> tagList = new ArrayList<>();
        tagList.add(randomNumberGenerator1.getRandomInteger().longValue());

        List<List<Object>> specificationList = new ArrayList<>();

        if (sizeTypeEnum == SizeTypeEnum.NONE)
        {
            addSpecification(specificationList, quantityOnHand, reorderQuantity, color, 0.0, sizeTypeEnum);
        }
        else
        {
            for (double size = sizeLower; size <= sizeUpper; size += 0.5)
            {
                addSpecification(specificationList, quantityOnHand, reorderQuantity, color, size, sizeTypeEnum);
            }
        }

        productEntitySessionBeanLocal.createNewProduct(newProductEntity, modelId, categoryId, saleId, tagList, specificationList);
    }

    private void addSpecification(List<List<Object>> specificationList, Integer quantityOnHand, Integer reorderQuantity, String color, Double size, SizeTypeEnum sizeTypeEnum) throws Exception
    {
        List<Object> specification = new ArrayList<>();

        specification.add(quantityOnHand);
        specification.add(reorderQuantity);
        specification.add(colorEntitySessionBeanLocal.retrieveColorByName(color));
        specification.add(sizeEntitySessionBeanLocal.retrieveSizeBySize(size, sizeTypeEnum));

        specificationList.add(specification);
    }

    /**
     * Product Creation Method End
     */
}
