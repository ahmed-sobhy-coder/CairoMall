package com.example.cairomall;

import android.content.Context;

import com.example.cairomall.Databse.GrocerySqliteDatabase;
import com.example.cairomall.Model.GroceryItemModel;

import java.util.ArrayList;

public class Utils {

    public static ArrayList<GroceryItemModel> allItemsInit(){
        ArrayList<GroceryItemModel> mItems;
        mItems= new ArrayList<>();
        mItems.add(new GroceryItemModel("Ice Cream", "Ice cream (derived from earlier iced cream or cream ice) is a sweetened frozen food typically eaten as a snack or dessert. It may be made from dairy milk or cream and is flavoured with a sweetener, either sugar or an alternative, and any spice, such as cocoa or vanilla.",
                "https://i.pinimg.com/originals/92/0a/04/920a04b1e48d250e437f4aa818a690ec.jpg",
                "Food", 5.4, 10,0,0,0));
        mItems.add(new GroceryItemModel("Milk", "Milk is a nutrient-rich, white liquid food produced by the mammary glands of mammals. It is the primary source of nutrition for infant mammals before they are able to digest other types of food.",
                "https://www.psdmockups.com/wp-content/uploads/2019/06/1L-Tetra-Pak-Carton-Boxes-PSD-Mockup.jpg",
                "Drink", 2.3, 8,0,0,0));
        mItems.add(new GroceryItemModel("Soda", "A soft drink is a drink that usually contains carbonated water, a sweetener, and a natural or artificial flavoring. The sweetener may be a sugar, high-fructose corn syrup, fruit juice, a sugar substitute, or some combination of these",
                "https://cdn.diffords.com/contrib/bws/2019/05/5cc9b8261f976.jpg",
                "Drink", 0.99, 15,0,0,0));
        mItems.add(new GroceryItemModel("Shampoo", "Shampoo is a hair care product, typically in the form of a viscous liquid, that is used for cleaning hair. Less commonly, shampoo is available in bar form, like a bar of soap. Shampoo is used by applying it to wet hair, massaging the product into the scalp, and then rinsing it out. Some users may follow a shampooing with the use of hair conditioner.",
                "https://res.cloudinary.com/mtree/image/upload/q_auto,f_auto/HeadandShoulders_PH_MW/9Gq7gblVJdM5RfPkfdp5H/6302bb00431710a9b9abf450a31b73e3/HS_PH_Menthol_Large.jpg",
                "Cleanser", 14.5, 9,0,0,0));
        mItems.add(new GroceryItemModel("Spaghetti",
                "Spaghetti is a long, thin, solid, cylindrical pasta. It is a staple food of traditional Italian cuisine. Like other pasta, spaghetti is made of milled wheat and water and sometimes enriched with vitamins and minerals. Italian spaghetti is typically made from durum wheat semolina.",
                "https://sc01.alicdn.com/kf/UTB8AoDnIJoSdeJk43Owq6ya4XXak.jpg_350x350.jpg",
                "Food", 3.85, 6,0,0,0));
        mItems.add(new GroceryItemModel("Soap", "Soap is a salt of a fatty acid[1] used in a variety of cleansing and lubricating products. In a domestic setting, soaps are surfactants usually used for washing, bathing, and other types of housekeeping. In industrial settings, soaps are used as thickeners, components of some lubricants, and precursors to catalysts.",
                "https://www.londondrugs.com/on/demandware.static/-/Sites-londondrugs-master/default/dwfcbde309/products/L9276163/large/L9276163.JPG",
                "Cleanser", 2.65, 14,0,0,0));
        mItems.add(new GroceryItemModel("Juice", "Juice is a drink made from the extraction or pressing of the natural liquid contained in fruit and vegetables. It can also refer to liquids that are flavored with concentrate or other biological food sources, such as meat or seafood, such as clam juice. Juice is commonly consumed as a beverage or used as an ingredient or flavoring in foods or other beverages, as for smoothies. Juice emerged as a popular beverage choice after the development of pasteurization methods enabled its preservation without using fermentation (which is used in wine production)",
                "https://dg6qn11ynnp6a.cloudfront.net/wp-content/uploads/2015/04/199373.jpg",
                "Drink", 3.45, 25,0,0,0));
        mItems.add(new GroceryItemModel("Walnut", "A walnut is the nut of any tree of the genus Juglans (Family Juglandaceae), particularly the Persian or English walnut, Juglans regia. A walnut is the edible seed of a drupe, and thus not a true botanical nut. It is commonly consumed as a nut.",
                "https://sc01.alicdn.com/kf/Uc583c440540142d89b55cc6fbde774106/969734566/Uc583c440540142d89b55cc6fbde774106.jpg",
                "Nuts", 5.6, 4,0,0,0));
        mItems.add(new GroceryItemModel("Pistachio", "The pistachio (/pɪˈstɑːʃiˌoʊ, -ˈstæ-/, Pistacia vera), a member of the cashew family, is a small tree originating from Central Asia and the Middle East. The tree produces seeds that are widely consumed as food. Pistacia vera often is confused with other species in the genus Pistacia that are also known as pistachio.",
                "https://sc01.alicdn.com/kf/UTB8kYzuIlahduJk43Jaq6zM8FXaz.jpg",
                "Nuts", 9.85, 15,0,0,0));
        return mItems;
    }
    static public ArrayList<String> getCategories(Context context){
        ArrayList<GroceryItemModel> items=null;
        ArrayList<String> categories=null;
        GrocerySqliteDatabase database=new GrocerySqliteDatabase(context);
        if(database!=null){
            categories=new ArrayList<>();
            items=database.getAllItems();
            if(items!=null){
                for(GroceryItemModel item:items){
                    if(!isAlreadyInList(item.getCategory(),categories)){
                        categories.add(item.getCategory());
                    }
                }
                return categories;
            }
        }
        return null;
    }
   private static boolean isAlreadyInList(String category,ArrayList<String> categoryList){
        if(categoryList.contains(category)){
            return true;
        }
        return false;
    }
    public static ArrayList<GroceryItemModel> getItemsByCategory(Context context,String category){
        ArrayList<GroceryItemModel> items=null;
        ArrayList<GroceryItemModel> catItems=null;
        GrocerySqliteDatabase database=new GrocerySqliteDatabase(context);
        if(database!=null){
            items=database.getAllItems();
            catItems=new ArrayList<>();
            for(GroceryItemModel item:items){
                if(item.getCategory().equalsIgnoreCase(category)){
                    catItems.add(item);
                }
            }
        }
        return  catItems;
    }

}
