/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.BrandEntitySessionBeanLocal;
import ejb.session.stateless.CategoryEntitySessionBeanLocal;
import ejb.session.stateless.ColorEntitySessionBeanLocal;
import ejb.session.stateless.CouponEntitySessionBeanLocal;
import ejb.session.stateless.ModelEntitySessionBeanLocal;
import ejb.session.stateless.SaleEntitySessionBeanLocal;
import ejb.session.stateless.SizeEntitySessionBeanLocal;
import ejb.session.stateless.TagEntitySessionBeanLocal;
import entity.BrandEntity;
import entity.CategoryEntity;
import entity.ColorEntity;
import entity.CouponEntity;
import entity.ModelEntity;
import entity.SaleEntity;
import entity.SizeEntity;
import entity.TagEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import util.enumeration.SizeTypeEnum;
import util.exception.BrandNotFoundException;
import util.formatter.ConsoleLogger;

/**
 *
 * @author Theodoric
 */
@Startup
@LocalBean
@Singleton
@DependsOn("UserInitializationSessionBean")
public class ProductDetailsInitializationSessionBean
{

    @EJB(name = "BrandEntitySessionBeanLocal")
    private BrandEntitySessionBeanLocal brandEntitySessionBeanLocal;

    @EJB(name = "modelEntitySessionBeanLocal")
    private ModelEntitySessionBeanLocal modelEntitySessionBeanLocal;

    @EJB(name = "colorEntitySessionBeanLocal")
    private ColorEntitySessionBeanLocal colorEntitySessionBeanLocal;

    @EJB(name = "sizeEntitySessionBeanLocal")
    private SizeEntitySessionBeanLocal sizeEntitySessionBeanLocal;

    @EJB(name = "tagEntitySessionBeanLocal")
    private TagEntitySessionBeanLocal tagEntitySessionBeanLocal;

    @EJB(name = "couponEntitySessionBeanLocal")
    private CouponEntitySessionBeanLocal couponEntitySessionBeanLocal;

    @EJB(name = "saleEntitySessionBeanLocal")
    private SaleEntitySessionBeanLocal saleEntitySessionBeanLocal;

    @EJB(name = "categoryEntitySessionBeanLocal")
    private CategoryEntitySessionBeanLocal categoryEntitySessionBeanLocal;

    public ProductDetailsInitializationSessionBean()
    {
    }

    @PostConstruct
    public void postConstruct()
    {
        try
        {
            ConsoleLogger.log("Started");
            brandEntitySessionBeanLocal.retrieveBrandByBrandId(1L);
            ConsoleLogger.log("No Generation");
        }
        catch (BrandNotFoundException ex)
        {
            initializeData();
            ConsoleLogger.log("Completed");
        }
    }

    /**
     * Product Details Creation Method Start
     */
    private void initializeData()
    {
        try
        {
            createBrands();
            createModels();
            createColors();
            createSizes();
            createCategories();
            createSales();
            createCoupons();
            createTags();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private void createBrands() throws Exception
    {
        List<BrandEntity> brandEntities = new ArrayList<>();

        brandEntities.add(new BrandEntity("A&R"));
        brandEntities.add(new BrandEntity("Bauer"));
        brandEntities.add(new BrandEntity("Bladetech"));        
        brandEntities.add(new BrandEntity("CCM"));
        brandEntities.add(new BrandEntity("Edea"));
        brandEntities.add(new BrandEntity("Elite"));
        brandEntities.add(new BrandEntity("Iceium"));
        brandEntities.add(new BrandEntity("Gam"));
        brandEntities.add(new BrandEntity("Graf"));
        brandEntities.add(new BrandEntity("Harlick"));
        brandEntities.add(new BrandEntity("Jackson"));
        brandEntities.add(new BrandEntity("Massive"));
        brandEntities.add(new BrandEntity("Monkeysports"));
        brandEntities.add(new BrandEntity("Renfrew"));
        brandEntities.add(new BrandEntity("Riedell"));
        brandEntities.add(new BrandEntity("Risport"));
        brandEntities.add(new BrandEntity("SP-Teri"));
        brandEntities.add(new BrandEntity("True"));
        brandEntities.add(new BrandEntity("Tuffterrys"));        
        brandEntities.add(new BrandEntity("Warrior"));
        brandEntities.add(new BrandEntity("Winnwell"));

        for (BrandEntity brandEntity : brandEntities)
        {
            brandEntitySessionBeanLocal.createNewBrand(brandEntity);
        }
    }

    private void createModels() throws Exception
    {
        Map<ModelEntity, Long> modelEntitiesBrandId = new HashMap<>();
        List<BrandEntity> brandEntities = brandEntitySessionBeanLocal.retrieveAllBrands();

        for (BrandEntity brandEntity : brandEntities)
        {
            modelEntitiesBrandId.put(new ModelEntity("N/A"), brandEntity.getBrandId());
        }

        modelEntitiesBrandId.put(new ModelEntity("Alpha"), brandEntitySessionBeanLocal.retrieveBrandByName("Warrior").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Covert"), brandEntitySessionBeanLocal.retrieveBrandByName("Warrior").getBrandId());

        modelEntitiesBrandId.put(new ModelEntity("Twigz"), brandEntitySessionBeanLocal.retrieveBrandByName("Monkeysports").getBrandId());

        modelEntitiesBrandId.put(new ModelEntity("Pro"), brandEntitySessionBeanLocal.retrieveBrandByName("Elite").getBrandId());

        modelEntitiesBrandId.put(new ModelEntity("Escalade X"), brandEntitySessionBeanLocal.retrieveBrandByName("SP-Teri").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Gold Medalist"), brandEntitySessionBeanLocal.retrieveBrandByName("SP-Teri").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("KT-2"), brandEntitySessionBeanLocal.retrieveBrandByName("SP-Teri").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("KT-3"), brandEntitySessionBeanLocal.retrieveBrandByName("SP-Teri").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Pro Teri"), brandEntitySessionBeanLocal.retrieveBrandByName("SP-Teri").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Super Teri"), brandEntitySessionBeanLocal.retrieveBrandByName("SP-Teri").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Zero Gravity"), brandEntitySessionBeanLocal.retrieveBrandByName("SP-Teri").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Zero Gravity X"), brandEntitySessionBeanLocal.retrieveBrandByName("SP-Teri").getBrandId());

        modelEntitiesBrandId.put(new ModelEntity("Chorus"), brandEntitySessionBeanLocal.retrieveBrandByName("Edea").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Concerto"), brandEntitySessionBeanLocal.retrieveBrandByName("Edea").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Ice Fly"), brandEntitySessionBeanLocal.retrieveBrandByName("Edea").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Motivo"), brandEntitySessionBeanLocal.retrieveBrandByName("Edea").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Overture"), brandEntitySessionBeanLocal.retrieveBrandByName("Edea").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Piano"), brandEntitySessionBeanLocal.retrieveBrandByName("Edea").getBrandId());

        modelEntitiesBrandId.put(new ModelEntity("Diamond"), brandEntitySessionBeanLocal.retrieveBrandByName("Riedell").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Emerald"), brandEntitySessionBeanLocal.retrieveBrandByName("Riedell").getBrandId());

        modelEntitiesBrandId.put(new ModelEntity("Bolero"), brandEntitySessionBeanLocal.retrieveBrandByName("Graf").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Caprice"), brandEntitySessionBeanLocal.retrieveBrandByName("Graf").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Davos Gold"), brandEntitySessionBeanLocal.retrieveBrandByName("Graf").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("F4000"), brandEntitySessionBeanLocal.retrieveBrandByName("Graf").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Galaxy"), brandEntitySessionBeanLocal.retrieveBrandByName("Graf").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Prestige"), brandEntitySessionBeanLocal.retrieveBrandByName("Graf").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Richmond Special"), brandEntitySessionBeanLocal.retrieveBrandByName("Graf").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Splendid Gold"), brandEntitySessionBeanLocal.retrieveBrandByName("Graf").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Windsor"), brandEntitySessionBeanLocal.retrieveBrandByName("Graf").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Ace"), brandEntitySessionBeanLocal.retrieveBrandByName("Graf").getBrandId());

        modelEntitiesBrandId.put(new ModelEntity("Electra Light"), brandEntitySessionBeanLocal.retrieveBrandByName("Risport").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("RF1 Elite"), brandEntitySessionBeanLocal.retrieveBrandByName("Risport").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("RF1 Exclusive"), brandEntitySessionBeanLocal.retrieveBrandByName("Risport").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("RF3 Pro"), brandEntitySessionBeanLocal.retrieveBrandByName("Risport").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Royal Elite"), brandEntitySessionBeanLocal.retrieveBrandByName("Risport").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Royal Exclusive"), brandEntitySessionBeanLocal.retrieveBrandByName("Risport").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Royal Pro"), brandEntitySessionBeanLocal.retrieveBrandByName("Risport").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Scott Hamilton"), brandEntitySessionBeanLocal.retrieveBrandByName("Risport").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Paerl"), brandEntitySessionBeanLocal.retrieveBrandByName("Risport").getBrandId());

        modelEntitiesBrandId.put(new ModelEntity("Excel"), brandEntitySessionBeanLocal.retrieveBrandByName("Jackson").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Mystique"), brandEntitySessionBeanLocal.retrieveBrandByName("Jackson").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Softskate"), brandEntitySessionBeanLocal.retrieveBrandByName("Jackson").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Evo Fusion"), brandEntitySessionBeanLocal.retrieveBrandByName("Jackson").getBrandId());

        modelEntitiesBrandId.put(new ModelEntity("Nexus"), brandEntitySessionBeanLocal.retrieveBrandByName("Bauer").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Supreme"), brandEntitySessionBeanLocal.retrieveBrandByName("Bauer").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Vapor"), brandEntitySessionBeanLocal.retrieveBrandByName("Bauer").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("NSX"), brandEntitySessionBeanLocal.retrieveBrandByName("Bauer").getBrandId());

        modelEntitiesBrandId.put(new ModelEntity("RibCor"), brandEntitySessionBeanLocal.retrieveBrandByName("CCM").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Tacks"), brandEntitySessionBeanLocal.retrieveBrandByName("CCM").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("JetSpeed"), brandEntitySessionBeanLocal.retrieveBrandByName("CCM").getBrandId());

        modelEntitiesBrandId.put(new ModelEntity("G Series"), brandEntitySessionBeanLocal.retrieveBrandByName("Graf").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("PeakSpeed"), brandEntitySessionBeanLocal.retrieveBrandByName("Graf").getBrandId());
        
        modelEntitiesBrandId.put(new ModelEntity("Figure Skates"), brandEntitySessionBeanLocal.retrieveBrandByName("Iceium").getBrandId());
        modelEntitiesBrandId.put(new ModelEntity("Hockey Skates"), brandEntitySessionBeanLocal.retrieveBrandByName("Iceium").getBrandId());

        for (Map.Entry<ModelEntity, Long> modelEntityBrandId : modelEntitiesBrandId.entrySet())
        {
            modelEntitySessionBeanLocal.createNewModel(modelEntityBrandId.getKey(), modelEntityBrandId.getValue());
        }
    }

    private void createColors() throws Exception
    {
        List<ColorEntity> colorEntities = new ArrayList<>();

        // Red
        colorEntities.add(new ColorEntity("Indian Red", "#CD5C5C"));
        colorEntities.add(new ColorEntity("Light Coral", "#F08080"));
        colorEntities.add(new ColorEntity("Salmon", "#FA8072"));
        colorEntities.add(new ColorEntity("Dark Salmon", "#E9967A"));
        colorEntities.add(new ColorEntity("Light Salmon", "#FFA07A"));
        colorEntities.add(new ColorEntity("Crimson", "#DC143C"));
        colorEntities.add(new ColorEntity("Red", "#FF0000"));
        colorEntities.add(new ColorEntity("Fire Brick", "#B22222"));
        colorEntities.add(new ColorEntity("Dark Red", "#8B0000"));

        // Pink
        colorEntities.add(new ColorEntity("Pink", "#FFC0CB"));
        colorEntities.add(new ColorEntity("Light Pink", "#FFB6C1"));
        colorEntities.add(new ColorEntity("Hot Pink", "#FF69B4"));
        colorEntities.add(new ColorEntity("Deep Pink", "#FF1493"));
        colorEntities.add(new ColorEntity("Medium Violet Red", "#C73585"));
        colorEntities.add(new ColorEntity("Pale Violet Red", "#DB7093"));

        // Orange 
        colorEntities.add(new ColorEntity("Coral", "#FF7F50"));
        colorEntities.add(new ColorEntity("Tomato", "#FF6347"));
        colorEntities.add(new ColorEntity("Orange Red", "#FF4500"));
        colorEntities.add(new ColorEntity("Dark Orange", "#FF8C00"));
        colorEntities.add(new ColorEntity("Orange", "#FFA500"));

        // Yellow
        colorEntities.add(new ColorEntity("Gold", "#FFD700"));
        colorEntities.add(new ColorEntity("Yellow", "#FFFF00"));
        colorEntities.add(new ColorEntity("Light Yellow", "#FFFFE0"));
        colorEntities.add(new ColorEntity("Lemon Chiffon", "#FFFACD"));
        colorEntities.add(new ColorEntity("Light Goldenrod Yellow", "#FAFAD2"));
        colorEntities.add(new ColorEntity("Papaya Whip", "#FFEFD5"));
        colorEntities.add(new ColorEntity("Moccasin", "#FFE4B5"));
        colorEntities.add(new ColorEntity("Peach Puff", "#FFDAB9"));
        colorEntities.add(new ColorEntity("Pale Goldenrod", "#EEE8AA"));
        colorEntities.add(new ColorEntity("Khaki", "#F0E68C"));
        colorEntities.add(new ColorEntity("Dark Khaki", "#BDB76B"));

        // Purple
        colorEntities.add(new ColorEntity("Lavender", "#E6E6FA"));
        colorEntities.add(new ColorEntity("Thistle", "#D8BFD8"));
        colorEntities.add(new ColorEntity("Plum", "#DDA0DD"));
        colorEntities.add(new ColorEntity("Violet", "#EE82EE"));
        colorEntities.add(new ColorEntity("Orchid", "#DA70D6"));
        colorEntities.add(new ColorEntity("Fuchsia", "#FF00FF"));
        colorEntities.add(new ColorEntity("Medium Orchid", "#BA55D3"));
        colorEntities.add(new ColorEntity("Medium Purple", "#9370DB"));
        colorEntities.add(new ColorEntity("Rebecca Purple", "#663399"));
        colorEntities.add(new ColorEntity("Blue Violet", "#8A2BE2"));
        colorEntities.add(new ColorEntity("Dark Violet", "#9250D3"));
        colorEntities.add(new ColorEntity("Dark Orchid", "#9932CC"));
        colorEntities.add(new ColorEntity("Dark Magenta", "#8B008B"));
        colorEntities.add(new ColorEntity("Purple", "#800080"));
        colorEntities.add(new ColorEntity("Indigo", "#4B0082"));
        colorEntities.add(new ColorEntity("Slate Blue", "#6A5ACD"));
        colorEntities.add(new ColorEntity("Dark Slate Blue", "#483D8B"));
        colorEntities.add(new ColorEntity("Medium Slate Blue", "#7B68EE"));

        // Green
        colorEntities.add(new ColorEntity("GreenYellow", "#ADFF2F"));
        colorEntities.add(new ColorEntity("Chartreuse", "#7FFF00"));
        colorEntities.add(new ColorEntity("LawnGreen", "#7CFC00"));
        colorEntities.add(new ColorEntity("Lime", "#00FF00"));
        colorEntities.add(new ColorEntity("Lime Green", "#32CD32"));
        colorEntities.add(new ColorEntity("Pale Green", "#98FB98"));
        colorEntities.add(new ColorEntity("Light Green", "#90EE90"));
        colorEntities.add(new ColorEntity("Medium Spring Green", "#00FA9A"));
        colorEntities.add(new ColorEntity("Spring Green", "#00FF7F"));
        colorEntities.add(new ColorEntity("Medium Sea Green", "#3CB371"));
        colorEntities.add(new ColorEntity("Sea Green", "#2E8B57"));
        colorEntities.add(new ColorEntity("Forest Green", "#228B22"));
        colorEntities.add(new ColorEntity("Green", "#008000"));
        colorEntities.add(new ColorEntity("Dark Green", "#006250"));
        colorEntities.add(new ColorEntity("Yellow Green", "#9ACD32"));
        colorEntities.add(new ColorEntity("Olive Drab", "#6B8E23"));
        colorEntities.add(new ColorEntity("Olive", "#808000"));
        colorEntities.add(new ColorEntity("Dark Olive Green", "#556B2F"));
        colorEntities.add(new ColorEntity("Medium Aquamarine", "#66CDAA"));
        colorEntities.add(new ColorEntity("Dark Sea Green", "#8FBC8B"));
        colorEntities.add(new ColorEntity("Light Sea Green", "#20B2AA"));
        colorEntities.add(new ColorEntity("Dark Cyan", "#008B8B"));
        colorEntities.add(new ColorEntity("Teal", "#008080"));

        // Blue
        colorEntities.add(new ColorEntity("Aqua", "#00FFFF"));
        colorEntities.add(new ColorEntity("Light Cyan", "#E0FFFF"));
        colorEntities.add(new ColorEntity("Pale Turquoise", "#AFEEEE"));
        colorEntities.add(new ColorEntity("Aquamarine", "#7FFFD4"));
        colorEntities.add(new ColorEntity("Turquoise", "#40E0D0"));
        colorEntities.add(new ColorEntity("Medium Turquoise", "#48D1CC"));
        colorEntities.add(new ColorEntity("Dark Turquoise", "#00CED1"));
        colorEntities.add(new ColorEntity("Cadet Blue", "#5F9EA0"));
        colorEntities.add(new ColorEntity("Steel Blue", "#4682B4"));
        colorEntities.add(new ColorEntity("Light Steel Blue", "#B0C4DE"));
        colorEntities.add(new ColorEntity("Powder Blue", "#B0E0E6"));
        colorEntities.add(new ColorEntity("Light Blue", "#ADD8E6"));
        colorEntities.add(new ColorEntity("Sky Blue", "#87CEEB"));
        colorEntities.add(new ColorEntity("Light Sky Blue", "#87CEFA"));
        colorEntities.add(new ColorEntity("DeepSky Blue", "#00BFFF"));
        colorEntities.add(new ColorEntity("Dodger Blue", "#1E90FF"));
        colorEntities.add(new ColorEntity("Cornflower Blue", "#6495ED"));
        colorEntities.add(new ColorEntity("Royal Blue", "#4169E1"));
        colorEntities.add(new ColorEntity("Blue", "#0000FF"));
        colorEntities.add(new ColorEntity("Medium Blue", "#0000CD"));
        colorEntities.add(new ColorEntity("Dark Blue", "#00008B"));
        colorEntities.add(new ColorEntity("Navy", "#000080"));
        colorEntities.add(new ColorEntity("Midnight Blue", "#191970"));

        // Brown
        colorEntities.add(new ColorEntity("Cornsilk", "#FFF8DC"));
        colorEntities.add(new ColorEntity("Blanched Almond", "#FFEBCD"));
        colorEntities.add(new ColorEntity("Bisque", "#FFE4C4"));
        colorEntities.add(new ColorEntity("Navajo White", "#FFDEAD"));
        colorEntities.add(new ColorEntity("Wheat", "#F5DEB3"));
        colorEntities.add(new ColorEntity("Burly Wood", "#DEB887"));
        colorEntities.add(new ColorEntity("Tan", "#D2B48C"));
        colorEntities.add(new ColorEntity("Rosy Brown", "#BC8F8F"));
        colorEntities.add(new ColorEntity("Sandy Brown", "#F4A460"));
        colorEntities.add(new ColorEntity("Goldenrod", "#DAA520"));
        colorEntities.add(new ColorEntity("Dark Goldenrod", "#B8860B"));
        colorEntities.add(new ColorEntity("Peru", "#CD853F"));
        colorEntities.add(new ColorEntity("Chocolate", "#D2691E"));
        colorEntities.add(new ColorEntity("Saddle Brown", "#8B4513"));
        colorEntities.add(new ColorEntity("Sienna", "#A0522D"));
        colorEntities.add(new ColorEntity("Brown", "#A52A2A"));
        colorEntities.add(new ColorEntity("Maroon", "#800000"));

        // White
        colorEntities.add(new ColorEntity("White", "#FFFFFF"));
        colorEntities.add(new ColorEntity("Snow", "#FFFAFA"));
        colorEntities.add(new ColorEntity("Honey Dew", "#F0FFF0"));
        colorEntities.add(new ColorEntity("Mint Cream", "#F5FFFA"));
        colorEntities.add(new ColorEntity("Azure", "#F0FFFF"));
        colorEntities.add(new ColorEntity("Alice Blue", "#F0F8FF"));
        colorEntities.add(new ColorEntity("GhostWhite", "#F8F8FF"));
        colorEntities.add(new ColorEntity("White Smoke", "#F5F5F5"));
        colorEntities.add(new ColorEntity("Sea Shell", "#FFF5EE"));
        colorEntities.add(new ColorEntity("Beige", "#F5F5DC"));
        colorEntities.add(new ColorEntity("Old ace", "#FDF5E6"));
        colorEntities.add(new ColorEntity("Floral White", "#FFFAF0"));
        colorEntities.add(new ColorEntity("Ivory", "#FFFFF0"));
        colorEntities.add(new ColorEntity("Antique White", "#FAEBD7"));
        colorEntities.add(new ColorEntity("Linen", "#FAF0E6"));
        colorEntities.add(new ColorEntity("Lavender Blush", "#FFF0F5"));
        colorEntities.add(new ColorEntity("Misty Rose", "#FFE4E1"));

        // Gray
        colorEntities.add(new ColorEntity("Gainsboro", "#DCDCDC"));
        colorEntities.add(new ColorEntity("LightGray", "#D3D3D3"));
        colorEntities.add(new ColorEntity("Silver", "#C0C0C0"));
        colorEntities.add(new ColorEntity("DarkGray", "#A9A9A9"));
        colorEntities.add(new ColorEntity("Gray", "#808080"));
        colorEntities.add(new ColorEntity("Dim Gray", "#696969"));
        colorEntities.add(new ColorEntity("Light Slate Gray", "#778899"));
        colorEntities.add(new ColorEntity("Slate Gray", "#708090"));
        colorEntities.add(new ColorEntity("Dark Slate Gray", "#2F4F4F"));
        colorEntities.add(new ColorEntity("Black", "#000000"));

        colorEntities.add(new ColorEntity("Clear", "#D5C9B9"));

        for (ColorEntity colorEntity : colorEntities)
        {
            colorEntitySessionBeanLocal.createNewColor(colorEntity);
        }
    }

    private void createSizes() throws Exception
    {
        List<SizeEntity> sizeEntities = new ArrayList<>();

        double size = 0.0;
        for (size = 1.0; size <= 20.0; size += 0.5)
        {
            sizeEntities.add(new SizeEntity(size, SizeTypeEnum.NORMAL));
        }

        for (size = 6.0; size <= 13.5; size += 0.5)
        {
            sizeEntities.add(new SizeEntity(size, SizeTypeEnum.YOUTH));
        }

        sizeEntities.add(new SizeEntity(0.0, SizeTypeEnum.NONE));

        for (SizeEntity sizeEntity : sizeEntities)
        {
            sizeEntitySessionBeanLocal.createNewSize(sizeEntity);
        }
    }

    private void createCategories() throws Exception
    {
        List<CategoryEntity> categoryEntities = new ArrayList<>();

        categoryEntities.add(new CategoryEntity("Skates", "sk"));
        categoryEntities.add(new CategoryEntity("Senior Ice Hockey Skates", "sr"));
        categoryEntities.add(new CategoryEntity("Junior Ice Hockey Skates", "jr"));
        categoryEntities.add(new CategoryEntity("Youth Ice Hockey Skates", "yo"));
        categoryEntities.add(new CategoryEntity("Figure Skates", "fr"));

        createCategories(categoryEntities);

        categoryEntities.add(new CategoryEntity("Accessories", "ac"));
        categoryEntities.add(new CategoryEntity("Pucks", "pu"));
        categoryEntities.add(new CategoryEntity("Tapes", "ta"));
        categoryEntities.add(new CategoryEntity("Nets", "ne"));
        categoryEntities.add(new CategoryEntity("Laces", "la"));
        categoryEntities.add(new CategoryEntity("Sticks", "st"));
        categoryEntities.add(new CategoryEntity("Runners", "ru"));
        categoryEntities.add(new CategoryEntity("Soakers", "so"));
        
        categoryEntities.add(new CategoryEntity("Customize", "cu"));

        createCategories(categoryEntities);
    }

    private void createCategories(List<CategoryEntity> categoryEntities) throws Exception
    {
        categoryEntities.set(0, categoryEntitySessionBeanLocal.createNewCategory(categoryEntities.get(0)));

        for (int i = 1; i < categoryEntities.size(); i++)
        {
            categoryEntities.get(i).setParentCategoryEntity(categoryEntities.get(0));
            categoryEntitySessionBeanLocal.createNewCategory(categoryEntities.get(i));
        }

        categoryEntities.clear();
    }

    private void createSales() throws Exception
    {
        for (long i = 5; i <= 100; i += 5)
        {
            BigDecimal discountRate = BigDecimal.valueOf(i).divide(BigDecimal.valueOf(100)).setScale(2);
            saleEntitySessionBeanLocal.createNewSale(createSale(discountRate, "Sale " + i));
        }
    }

    private SaleEntity createSale(BigDecimal discountRate, String description)
    {
        LocalDateTime startDateTime = LocalDateTime.now().plusHours(2);
        LocalDateTime endDateTime = startDateTime.plusMonths(6);

        SaleEntity saleEntity = new SaleEntity(discountRate, startDateTime, endDateTime, description);

        return saleEntity;
    }

    private void createCoupons() throws Exception
    {
        for (long i = 5; i <= 100; i += 5)
        {
            BigDecimal discountRate = BigDecimal.valueOf(i).divide(BigDecimal.valueOf(100)).setScale(2);
            couponEntitySessionBeanLocal.createNewCoupon(createCoupon(discountRate, "Coupon " + i, discountRate.multiply(BigDecimal.valueOf(100)).add(BigDecimal.valueOf(50))));
        }
    }

    private CouponEntity createCoupon(BigDecimal discountRate, String description, BigDecimal minimumSpend)
    {
        LocalDateTime startDateTime = LocalDateTime.now().plusHours(1);
        LocalDateTime endDateTime = startDateTime.plusMonths(2);

        CouponEntity couponEntity = new CouponEntity(minimumSpend, 1000, discountRate, startDateTime, endDateTime, description);

        return couponEntity;
    }

    private void createTags() throws Exception
    {
        List<TagEntity> tagEntities = new ArrayList<>();

        tagEntities.add(new TagEntity("New", "New"));
        tagEntities.add(new TagEntity("Hot", "Hot"));
        tagEntities.add(new TagEntity("Clearance", "Clearance"));
        tagEntities.add(new TagEntity("HeatMoldable", "Heat Moldable"));

        for (TagEntity tagEntity : tagEntities)
        {
            tagEntitySessionBeanLocal.createNewTag(tagEntity);
        }
    }

    /**
     * Product Details Creation Method End
     */
}
