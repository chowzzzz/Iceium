/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.initialization;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Theodoric
 */
public class ProductData
{

    private Map<String, String> productDescription;

    public ProductData()
    {
        this.productDescription = new HashMap<>();
        initialize();
    }

    public void initialize()
    {
        figureSkates();

        seniorIceHockeySkates();

        juniorIceHockeySkates();

        youthIceHockeySkates();

        accessories();
    }

    private void figureSkates()
    {
        this.productDescription.put("Edea Piano Ice Skates", "In the world of skating equipment, where everything seemed to be said and done, a new word has been spoken by Edea. The renowned Italian maestro of skating boots has surprised its fans with yet another masterpiece: EDEA PIANO. These newly developed skating boots represent a combination of beauty, efficiency and hi-tech going in line with the contemporary trend in sports equipment manufacturing.");
    }

    private void seniorIceHockeySkates()
    {
        /**
         * Bauer
         */
        this.productDescription.put("Bauer Supreme S29 Senior Ice Hockey Skates", "New for 2019 are the Bauer Supreme S29 Senior Ice Hockey Skates! Built with an anatomical fit, featuring some high-end technology, the Supreme S29 Hockey Skates are a great option at an even better price point.\n" +
                "\n" +
                "Jumping right into it, the quarter package of the Supreme S29 Hockey Skates is made up of a 3D fiber composite+ material. This new setup is very lightweight, keeping players mobile on their skates, but also highly protective against a wide array of in-game impacts such as blocked shots, stick checks, and so on. The tongue is a 2-piece design, utilizing 48oz felt materials and a high-density reinforced metatarsal guard, providing players with great comfort and protection.\n" +
                "\n" +
                "Moving to the interior of the Supreme S29 Hockey Skates, they feature a Hydrophobic microfiber liner that helps wick moisture away from the foot, ensuring as little slippage as possible while the skates are in use. This translates to more energy transferred directly from your skates to the ice within every skating stride. The ankles are well protected too, thanks to the lightweight memory foam pads!\n" +
                "\n" +
                "Helping keep your foot locked into place are the Form-fit+ footbeds. These footbeds feature a grippy material on them, to really keep your feet stable while making hard cuts.\n" +
                "\n" +
                "To conclude, another upgrade found within the Supreme S29 Hockey Skates is the new Fiber Composite outsole. This outsole will help improve skating stability during hard acceleration and turning, while remaining very lightweight.\n" +
                "\n" +
                "If you are a high-level hockey player looking for a pair of skates that feature high-end specs, but don’t want to break the bank, check out the Bauer Supreme S29 Senior Ice Hockey Skates!");

        this.productDescription.put("Bauer Supreme S35 Senior Ice Hockey Skates", "New for the 2020 season are the Bauer Supreme S35 Senior Ice Hockey Skates. Bauer looks to follow a suit of the highly successful Supreme line by adding a few key upgrades to this current iteration, making them a great skate for any player.\n" +
                "\n" +
                "Starting with the S35 Quarter Package, Bauer has used their 3D Poly Carbon which is a lightweight material designed to be durable and protective for the player to get the most of this skate. Down at the toe, a regular toe design provides protection to the player.\n" +
                "\n" +
                "Up further on the tongue of the S35, a 30oz Felt tongue with Mesh Metatarsal Guard provides a thick protection for the player to increase the protection in this area.\n" +
                "\n" +
                "Moving on to the interior of the skate, Bauer has gone with a Microfiber liner to help create a durable and comfortable fit. Around on the ankle padding, a lightweight memory foam padding is used to create a comfortable fit that forms around the players ankle.\n" +
                "\n" +
                "Finally moving down some of the biggest changes on the Outsole, Blade Holder, and Runner of the S35. A TPR outsole creates a sturdy structure for optimal energy transfer. Down further, the Blade Holder is the TUUK Lightspeed Pro II holder and the runner is a stainless-steel blade.\n" +
                "\n" +
                "If you are an entry-level player looking for a skate whose performance will match yours, look no further than the Bauer Supreme S35 Senior Ice Hockey Skates.");

        this.productDescription.put("Bauer Supreme S36 Senior Ice Hockey Skates", "New for the 2020 season are the Bauer Supreme S36 Senior Ice Hockey Skates. Bauer looks to add another skate to the highly successful Supreme line by combining a few key upgrades to its current iterations, making them a great mid-level skate for any player.\n" +
                "\n" +
                "Starting with the S36 Quarter Package, Bauer has used their 3D Poly Carbon which is a lightweight material designed to be durable and protective for the player to get the most of this skate. Down at the toe, a regular toe design provides protection to the player.\n" +
                "\n" +
                "Up further on the tongue of the S36, a 30oz, 2 piece Felt tongue with Mesh Metatarsal Guard provides thick protection for the player. This helps to increase comfort and protection across the front of the skate.\n" +
                "\n" +
                "Moving to the interior of the Supreme S36 Hockey Skates, they feature a microfiber liner. This liner is specifically designed to keep players feet dry and comfortable by working to wick away moisture. Paired with this liner are the memory foam pads within the ankles of the boot. This foam delivers premium thermoformability, great comfort and durability, and an improved fit!\n" +
                "\n" +
                "Finally moving down to the holder of the Supreme S36 Ice Hockey Skates, Bauer is using their popular TUUK Lightspeed Edge holder than has been tested and played with at elite levels for years now. The runner is a stainless-steel blade.\n" +
                "\n" +
                "If you are a mid-level player looking for a skate that combines tech from Bauer’s highly successful Supreme and Vapor line, look no further than the Bauer Supreme S36 Senior Ice Hockey Skates.");

        this.productDescription.put("Bauer Supreme S37 Senior Ice Hockey Skates", "New for the 2020 season are the Bauer Supreme S37 Senior Ice Hockey Skates. Bauer looks to follow a suit of the highly successful Supreme line by adding a few key upgrades to this current iteration, making them a great skate for any player.\n" +
                "\n" +
                "Starting with the S37 Quarter Package, Bauer has used their Fiber Composite which is a lightweight material designed to be durable and protective for the player to get the most of this skate. Down at the toe, a regular toe design provides protection to the player.\n" +
                "\n" +
                "Up further on the tongue of the S37, a 40oz Felt tongue with Mesh Metatarsal Guard provides a thick protection for the player to increase the protection in this area.\n" +
                "\n" +
                "Moving on to the interior of the skate, Bauer has gone with a Microfiber liner to help create a durable and comfortable fit. Around on the ankle padding, a lightweight memory foam padding is used to create a comfortable fit that forms around the players ankle.\n" +
                "\n" +
                "Finally moving down some of the biggest changes on the Outsole, Blade Holder, and Runner of the S37. A TPU outsole creates a sturdy structure for optimal energy transfer. Down further, the Blade Holder is the popular TUUK Lightspeed Edge holder than has been tested and played with at elite levels for years now. The runner is a stainless-steel blade.\n" +
                "\n" +
                "If you are an intermediate-level player looking for a skate whose performance will match yours, look no further than the Bauer Supreme S37 Senior Ice Hockey Skates.");

        this.productDescription.put("Bauer Supreme UltraSonic Senior Ice Hockey Skates", "New for the 2020 season are the Bauer Supreme UltraSonic Senior Ice Hockey Skates. Bauer looks to follow a suit of the highly successful Supreme line by adding a few key upgrades to this current iteration, making them the most advanced Bauer skates to date.\n" +
                "\n" +
                "For the UltraSonic skates, Bauer has continued with the highly successful 3D Lasted Carbon Curved Composite Quarter Package which is setup to be more responsive to heat molding than ever before. This Quarter Package will provide an anatomical, close to the foot feel, making your strides on the ice even more efficient. Not only is this setup lightweight and durable, but it is also comfortable on your foot.\n" +
                "\n" +
                "One of the biggest changes to the UltraSonic is the addition of the Asymmetrical toe cap which is designed so the skater can get closer to the ice with an aggressive attack angle to allow for more efficient strides.\n" +
                "\n" +
                "This goes perfectly along with the new Reflex Pro Tongue featuring a double injected insert which will increase the overall comfortability. Further on this tongue, the Reflex Pro is designed to be heat moldable with composite inserts which provides a custom fit on the top of your foot as well!\n" +
                "\n" +
                "Moving on to the interior of the skate, Bauer has gone with their Lock Fit Pro liner which is designed to keep your feet locked into place, as well as quickly wick away sweat, providing you with ultimate feel and control while on the ice.\n" +
                "\n" +
                "This liner is paired a brand-new Aerofoam Pro Interior padding around the ankle to provide maximum comfortability on the ice. Finally, in this area, a reflex tendon guard eliminates discomfort that may occur with other tendon guards while being flexible for a trusted fit.\n" +
                "\n" +
                "Finally moving down some of the biggest changes on the Outsole, Blade Holder, and Runner of the UltraSonic. Brand-new Unibody Technology is combined with 3D lasted carbon CURV composite boot to create an incredibly lightweight design that will provide quality energy transfer on the ice.\n" +
                "\n" +
                "Down to the final pieces of the UltraSonic, the Blade Holder is the popular TUUK Lightspeed Edge holder than has been tested and played with at elite levels for years now. The Runner is the brand-new LS Pulse TI Edge Runner with power profile, designed to last longer, give the player a tighter turn radius, and improve glide speed.\n" +
                "\n" +
                "If you are an elite-level player looking for a skate whose performance will match yours, look no further than the Bauer Supreme UltraSonic Senior Ice Hockey Skates.");

        this.productDescription.put("Bauer Supreme 2S Senior Ice Hockey Skates", "New for the 2018 season are the Bauer Supreme 2S Senior Ice Hockey Skates. Bauer looks to follow suit of the highly successful Supreme line by adding a few key upgrades to this current iteration, making them one of the most advanced skates on the market.\n" +
                "\n" +
                "For the 2S skates, Bauer focused on lightweight and durability. With this in mind, they have introduced their new 3D lasted carbon Curv composite quarter package. With this setup being more responsive to heat molding than ever before, it will provide an anatomical, close to the foot feel, making your strides on the ice even more efficient. Not only is this setup lightweight and durable, but it is also comfortable on your feet! v\n" +
                "\n" +
                "This goes hand in hand with the introduction of Bauer’s new Reflex Tongue. This tongue is a 3-piece, 48-ounce felt tongue with an injected metatarsal guard giving you the perfect blend of comfort and protection.\n" +
                "\n" +
                "Moving on to the interior of the skate, Bauer has gone with their Lock Fit liner. This material is designed to keep your feet locked into place, as well as quickly wick away sweat, providing you with ultimate feel and control while on the ice.\n" +
                "\n" +
                "The Bauer Supreme 2S Senior Ice Hockey Skates feature the patented LightSpeed EDGE holder. These holders give you the option to quickly switch out a runner on the fly, if one were to break.\n" +
                "\n" +
                "If you are an elite-level player looking for a skate whose performance will match yours, look no further than the Bauer Supreme 2S Senior Ice Hockey Skates!");

        this.productDescription.put("Bauer Supreme 3S Senior Ice Hockey Skates", "New for the 2020 season are the Bauer Supreme 3S Senior Ice Hockey Skates. Bauer looks to follow a suit of the highly successful Supreme line by adding a few key upgrades to this current iteration, making them a great skate for any player.\n" +
                "\n" +
                "Starting with the 3S Quarter Package, Bauer has used their 3D Lasted Flex Composite which is a lightweight material designed to be durable and protective for the player to get the most of this skate. Down at the toe, a regular toe design provides protection to the player.\n" +
                "\n" +
                "Up further on the tongue of the 3S, a 48oz Felt tongue with exposed Injected Metatarsal Guard which provides a thick protection for the player to increase the protection in this area.\n" +
                "\n" +
                "Moving on to the interior of the skate, Bauer has gone with a Hydrophobic Microfiber liner to help wick moisture inside of the skate to keep the players foot dry. Around on the ankle padding, an Aerofoam padding provides the player with comfort while in use.\n" +
                "\n" +
                "Finally moving down some of the biggest changes on the Outsole, Blade Holder, and Runner of the 3S. A Pro TPU outsole creates a sturdy structure for optimal energy transfer.\n" +
                "\n" +
                "Down further, the Blade Holder is the popular TUUK Lightspeed Edge holder than has been tested and played with at elite levels for years now. The Runner is the LS1 blade with a Power Profile to give more efficient pushes.\n" +
                "\n" +
                "If you are an intermediate-level player looking for a skate whose performance will match yours, look no further than the Bauer Supreme 3S Senior Ice Hockey Skates.");

        this.productDescription.put("Bauer Vapor 2x Senior Ice Hockey Skates", "Bauer Hockey is proud to unveil their latest addition to their Vapor skate line with the introduction of the Bauer Vapor 2X Senior Ice Hockey Skates! With key improvements made to nearly every part of the skates, Bauer looks to offer ultimate performance every time you step onto the ice!\n" +
                "\n" +
                "Starting with the build of the Vapor 2X Hockey Skates, the quarter package is built with Bauer’s Acceflex system. Rather than going with a highly stiff boot, this design allows for a very slight amount of flex within the boot, allowing it to conform to the natural shape of your foot, which helps with overall performance and comfort! This new design is constructed out of Bauer’s 3-D lasted Carbon Curv Composite material with an X-Rib pattern on the back and Comfort Edge padding within the boot. This setup provides a familiar snug, close to the foot feel. Additionally, it is incredibly lightweight and durable as well, thanks to the Curv Composite material. The X-Rib pattern on the back of the skates helps lock the heel into place for quicker crossovers and tighter turns!\n" +
                "\n" +
                "Going hand in hand with the Carbon Curv boot construction is the Recoil tongue. This new design provides players with improved forward flex meaning more range of motion during skating strides. This tongue also excels in comfort and protection thanks to the molded felt and dual injected metatarsal guard better protecting the top of your foot from blocked shots or stick checks. On the comfort aspect, the tongue is constructed of three-piece 48 ounce felt!\n" +
                "\n" +
                "Staying true to the asymmetrical concept seen within the Vapor, the Vapor 2X Hockey Skates take it a step further with the introduction of their new asymmetrical toe cap and box! Using the profile of 25,000 player scans, Bauer has optimized this new design to enhance your push-off power, helping increase top-end speed! Compared to its predecessor, this new toe cap and box is a touch shorter in height and longer in length.\n" +
                "\n" +
                "Moving to the interior of the Vapor 2X Hockey Skates, they feature Bauer’s Lock-Fit liner. This liner is specifically designed to keep players feet dry and comfortable, as well as locked into place during gameplay in order to maximize skating efficiency thanks to a specialized grip on the lower half of the liner. Paired with the Lock-Fit liner are the Aerofoam+ memory foam pads within the ankles of the boot. This all-new foam delivers premium thermoformability, great comfort and durability, and an improved fit!\n" +
                "\n" +
                "The lower half of the Vapor 2X Hockey Skates feature Bauer’s tried-and-true LightSpeed EDGE holders. These holders are three millimeters taller than older model LightSpeed holders allowing players to get lower on their turns. Paired with the LightSpeed EDGE holders are the LS3 runners. These high-grade stainless-steel runners are three millimeters taller than their predecessor, the LS2 runners, giving players an increased angle of attack, and increases blade longevity from sharpenings!\n" +
                "\n" +
                "With the additions of the Accuflex system, Recoil tongue, and the asymmetrical toe cap, every element of the Bauer Vapor 2X Senior Ice Hockey Skates offers ultimate performance and a quality feel every time you put them on your feet!");

        this.productDescription.put("Bauer Vapor 2x Pro Senior Ice Hockey Skates", "Bauer Hockey is proud to unveil their latest addition to their Vapor skate line with the introduction of the Bauer Vapor 2X Pro Senior Ice Hockey Skates! With key improvements made to nearly every part of the skates, Bauer looks to offer ultimate performance every time you step onto the ice!\n" +
                "\n" +
                "Starting with the build of the Vapor 2X Pro Hockey Skates, the quarter package is built with Bauer’s new Dynaflex system. Rather than going with a highly stiff boot, this design allows for a very slight amount of flex within the boot, allowing it to conform to the natural shape of your foot, which helps with overall performance and comfort! This new design is constructed out of Bauer’s 3-D lasted Carbon Curv Composite material with an X-Rib pattern on the back and Comfort Edge padding within the boot. This setup provides a familiar snug, close to the foot feel. Additionally, it is incredibly lightweight and durable as well, thanks to the Curv Composite material. The X-Rib pattern on the back of the skates helps lock the heel into place for quicker crossovers and tighter turns!\n" +
                "\n" +
                "Going hand in hand with the Carbon Curv boot construction is the Recoil Pro Dual injected tongue! This new design provides players with improved forward flex meaning more range of motion during skating strides. This tongue also excels in comfort and protection thanks to the molded felt and dual injected metatarsal guard better protecting the top of your foot from blocked shots or stick checks.\n" +
                "\n" +
                "Staying true to the asymmetrical concept seen within the Vapor line, the Vapor 2X Pro Hockey Skates take it a step further with the introduction of their new asymmetrical toe cap and box! Using the profile of 25,000 player scans, Bauer has optimized this new design to enhance your push-off power, helping increase top-end speed! Compared to its predecessor, this new toe cap and box is a touch shorter in height and longer in length.\n" +
                "\n" +
                "Moving to the interior of the Vapor 2X Pro Hockey Skates, they feature Bauer’s Lock-Fit Pro liner. This liner is specifically designed to keep players feet dry and comfortable, as well as locked into place during gameplay in order to maximize skating efficiency thanks to a specialized grip on the lower half of the liner. Paired with the Lock-Fit Pro liner are the Aerofoam Pro memory foam pads within the ankles of the boot. This all-new foam delivers premium thermoformability, enhanced comfort and durability, and an improved fit!\n" +
                "\n" +
                "The lower half of the Vapor 2X Pro Hockey Skates feature Bauer’s tried-and-true LightSpeed EDGE holders. These holders are three millimeters taller than older model LightSpeed holders allowing players to get lower on their turns. Paired with the LightSpeed EDGE holders are the new LS5 carbon coated runners! These runners are two millimeters taller than older models giving players an improved turning radius. The black carbon coating on the LS5 runners gives the blades a longer sharpening life and better helps prevent against corrosion.\n" +
                "\n" +
                "With the additions of the Dynaflex system, Recoil Pro tongue, and the asymmetrical toe cap, every element of the Bauer Vapor 2X Pro Senior Ice Hockey Skates offers ultimate performance and a premium feel every time you put them on your feet!");

        this.productDescription.put("Bauer Vapor X2.5 Senior Ice Hockey Skates", "Built with the new 3-D Polycarb quarter package the Bauer Vapor X2.5 Senior Ice Hockey Skates, feature great comfort as soon as you put your foot into the skates, increased visual aesthetics, and additional flex within the boot. The one-piece 3-D Polycarb quarter construction provides players with added comfort and flex, helping the boot form to the natural shape of players’ feet.\n" +
                "\n" +
                "The tongue is constructed of Bauer’s 30-ounce felt tongue with metatarsal guards. This setup provides players with a highly comfortable and protective fit and feel!\n" +
                "\n" +
                "Moving to the interior of the Vapor X2.5 Hockey Skates, they feature a microfiber liner. This liner is specifically designed to keep players feet dry and comfortable by working to wick away moisture. Paired with this liner are the memory foam pads within the ankles of the boot. This foam delivers premium thermoformability, good comfort and durability, and an improved fit!\n" +
                "\n" +
                "The lower half of the Vapor X2.5 Hockey Skates feature Bauer’s LightSpeed PRO II holders, paired with stainless steel runners. This setup is highly durable and lightweight, and thanks to the stainless-steel runners, provide increased longevity on skate sharpenings.\n" +
                "\n" +
                "If you are a recreational-level player looking for a pair of skates that provide a good overall fit and good durability, look no further than the Bauer Vapor X2.5 Senior Ice Hockey Skates!");

        this.productDescription.put("Bauer Vapor X2.7 Senior Ice Hockey Skates", "Built with the new Comfort Flex System the Vapor X2.7 Hockey Skates, feature great comfort as soon as you put your foot into the skates, increased visual aesthetics, and additional flex within the boot. The one-piece 3-D Polycarb quarter construction provides players with added comfort and flex, helping the boot form to the natural shape of players’ feet.\n" +
                "\n" +
                "The tongue is constructed of Bauer’s two-piece 40-ounce tongue with injected metatarsal guards. This setup provides players with a highly comfortable and protective fit and feel!\n" +
                "\n" +
                "Moving to the interior of the Vapor X2.7 Hockey Skates, they feature a microfiber liner. This liner is specifically designed to keep players feet dry and comfortable by working to wick away moisture. Paired with this liner are the memory foam pads within the ankles of the boot. This foam delivers premium thermoformability, great comfort and durability, and an improved fit!\n" +
                "\n" +
                "The lower half of the Vapor X2.7 Hockey Skates feature Bauer’s tried-and-true LightSpeed EDGE holders. These holders are three millimeters taller than older model LightSpeed holders allowing players to get lower on their turns. Paired with the LightSpeed EDGE holders are stainless steel runners. These runners offer improved longevity on skate sharpenings thanks to the material.\n" +
                "\n" +
                "With the additions of the Comfort Flex system, and the one-piece Polycarb quarter package, every element of the Bauer Vapor X2.7 Senior Ice Hockey Skates offers high-end performance and a quality feel every time you put them on your feet!");

        this.productDescription.put("Bauer Vapor X2.9 Senior Ice Hockey Skates", "Bauer Hockey is proud to unveil their latest addition to their Vapor skate line with the introduction of the Bauer Vapor X2.9 Senior Ice Hockey Skates! With key improvements made to nearly every part of the skates, Bauer looks to offer ultimate performance every time you step onto the ice!\n" +
                "\n" +
                "Starting with the build of the Vapor X2.9 Hockey Skates, the quarter package is built with Bauer’s Perform Flex system. Rather than going with a highly stiff boot, this design allows for a very slight amount of flex within the boot, allowing it to conform to the natural shape of your foot, which helps with overall performance and comfort! This new design is constructed out of Bauer’s 3-D lasted Tech Mesh material with an X-Rib pattern on the back and Comfort Edge padding within the boot. This setup provides a familiar snug, close to the foot feel. Additionally, it is highly lightweight and durable as well. The X-Rib pattern helps lock the heel into place for quicker crossovers and tighter turns!\n" +
                "\n" +
                "Going hand in hand with the Tech Mesh boot construction is Bauer’s two-piece 48-ounce tongue with injected metatarsal guards. This setup provides players with a highly comfortable and protective fit and feel!\n" +
                "\n" +
                "Moving to the interior of the Vapor X2.9 Hockey Skates, they feature a hydrophobic microfiber liner. This liner is specifically designed to keep players feet dry and comfortable by working to wick away moisture. Paired with this liner are the Aerofoam memory foam pads within the ankles of the boot. This all-new foam delivers premium thermoformability, great comfort and durability, and an improved fit!\n" +
                "\n" +
                "The lower half of the Vapor X2.9 Hockey Skates feature Bauer’s tried-and-true LightSpeed EDGE holders. These holders are three millimeters taller than older model LightSpeed holders allowing players to get lower on their turns. Paired with the LightSpeed EDGE holders are the LS1 runners. These high-grade stainless-steel runners feature improved longevity on skate sharpenings!\n" +
                "\n" +
                "With the additions of the Perform Flex system, the 3-D lasted Tech Mesh Quarter, and the Comfort Edge padding, every element of the Bauer Vapor X2.9 Senior Ice Hockey Skates offers high-end performance and a quality feel every time you put them on your feet!");

        this.productDescription.put("Bauer Vapor X800 Senior Ice Hockey Skates - '17 Model", "Make your last stride as quick as your first and leave your opponent in the dust with the new Bauer Vapor X800 Senior Ice Hockey Skate. New for 2017, the Bauer Vapor X800 skate builds upon the concepts that made the 2015 Vapor line one of the top models of skates on the market, with new improvements for unmatched speed and quickness.\n" +
                "\n" +
                "The Bauer Vapor X800 Senior Ice Hockey skate is a high level skate designed to lock a skater's foot in for achieving better mobility and speed. The Vapor X800 will be constructed using the ultra-lightweight Curv composite material, which reduces the overall weight of the skate while keeping the durability, strength, and support that players are used to with the top end skates. The X-rib pattern will continue the heel and ankle lock design to keep the skaters foot in place for quicker crossovers and tighter turns.\n" +
                "\n" +
                "Moving to the inside of the skate, the ankle of the X800 will feature dual density foam to provide a comfortable and supporting fit inside of the skate. The Lock-Fit liner is designed to help keep the foot in place while skating. This combined with tapered V-fit design of the skate help to lock the fit in for a more efficient stride. The Flex-Lock tongue will offer good forward flex while locking the skater's foot down, and the Lock-Fit footbed will help keep the foot stabilized in the boot. All these designs will keep the skaters foot in place to help achieve better agility and speed.\n" +
                "\n" +
                "Coming from the boot will be Bauer's Lightspeed Edge holder. This revolutionary holder uses a trigger system to make swapping out damaged or broken steel quick and easy. The holder will also sit three milometers higher than older model Tuuk holders. By raising and narrowing the holder, skaters can get lower into turns without bottoming out. Bauer's LS2 steel will come standard on the Vapor X800 skate.\n" +
                "\n" +
                "If you are a high level hockey player looking for speed and performance, look no further than the Bauer Vapor X800 Senior Ice Hockey skate.");

        /**
         * CCM
         */
        this.productDescription.put("CCM Super Tacks AS1 Senior Ice Hockey Skates", "We're so sure that you'll love your new CCM Super Tacks AS1 Ice Hockey Skates that if you aren't satisfied for any reason, we'll take them back within 90 days of purchase and issue a full refund - no questions asked. This offer is valid on qualifying CCM Super Tacks skates purchased between April 28th, 2018 and October 1st, 2018. Customers who wish to return their skates need to call customer service to initiate a standard merchandise return within the 90 day window. Upon receipt and inspection of the skates, the customer will be provided a refund for the full purchase amount of the skates. Please allow up to 7 days for us to process your return and issue a refund.\n" +
                "\n" +
                "For 2018, CCM builds off of their successful one-piece boot construction and added key upgrades to the CCM Super Tacks AS1 Senior Ice Hockey Skate.\n" +
                "\n" +
                "CCM made key upgrades to the Super Tacks AS1 skate to offer more comfort and protection. The overall height of the boot has been increased by 5mm to ensure a better wrap around the high ankle all while providing better comfort when skating. The increased size of the comfort pads at the top of the skate will relieve the pressure top players put on the high ankle area when getting over on their edges. Upgraded stitching in the eyelets means the skate will provide better durability throughout their usage. Super Tacks AS1 skates now feature a sublimated graphics package with removed stitching, thus making the boot lighter than models prior.\n" +
                "\n" +
                "On the outside, the CCM Super Tacks AS1 Senior Ice Hockey Skate will feature the Monoframe 360 one-piece boot which is designed to increase the amount of contact between the foot and skate. The anatomical 360-degree fit wraps around the foot for better fit and control within the skate. Moving to the inside of the boot, the core will be constructed with CCM’s Speedcore 3 stiffness. This heat moldable core is super lightweight and stiff which allows skaters to generate more power from their stride.\n" +
                "\n" +
                "Internally, CCM is featuring asymmetrical multi density memory foam to provide a more detailed custom style fit inside the Super Tacks. On top of the foams, AS1 skates come with the TotalDri Pro liner that will wick away moisture to keep the foot locked and dry throughout the game. CCM also added larger abrasion spots in the high wear areas to ensure the skates can handle the grind of a long season.\n" +
                "\n" +
                "CCM’s Super Tacks AS1 Skates will come with the Tritech Flex tongue. This molded tongue will give players maximum forward flex while offering top lace bite protection. These skates will also come with CCM’s new Orthomove insoles which will offer custom arch support inserts to ensure the best fit to the skater.\n" +
                "\n" +
                "Finishing off the AS1 skates will come with the SpeedBlade 4.0 holder which offers pro level durability and increased attack angle for players to get lower on their edges. CCM’s top level Speedblade Black steel will come standard on the AS1 which features a harder oxide treated runner to provide longer edge life and better durability.\n" +
                "\n" +
                "If you are an explosive skater looking to put more power in your stride, check out the CCM Super Tacks AS1 Senior Ice Hockey Skates.");

        this.productDescription.put("CCM Super Tacks AS3 Senior Ice Hockey Skates", "CCM Hockey is constantly pushing the boundaries when it comes to ice hockey skate innovation and this time is no different with the Super Tacks AS3 Senior Hockey Skates! With the incorporation of the One-Piece boot, improved interior liner, and the SpeedBlade XS holders, the Super Tacks AS3 Skates offer the complete package of performance, comfort, and efficiency.\n" +
                "\n" +
                "Starting with the fit, the Super Tacks AS3 Skates feature the same tried and true anatomical fit that players are accustomed to with the Super Tacks line. This form-fitting, 360-degree fit around the forefoot, heel, and ankle helps eliminate negative space within the boot, allowing for more efficient energy transfer meaning improved speed and agility!\n" +
                "\n" +
                "Building upon that, the Super Tacks AS3 Skates utilize CCM’s one-piece “Monoframe” quarter package. This one-piece build is the epitome of pure performance, built with a RFM composite material that is both lightweight and durable. The result of this setup is superior energy transfer from the skating stride, to the feet, to the ice.\n" +
                "\n" +
                "Taking customization to another level is CCM’s incorporation of the XS Tongues on the Super Tacks AS3 Skates. These easily-swappable skate tongues allow players to switch them out based on their personal preference of fit, feel, and protection. Furthermore, each one of the three tongue models are built with a D30 Smart Material that helps disperse the force of direct impacts, such as blocked shots, away from the sensitive ankle areas. Simply use the two clips at the base of the tongues to change them out and perfect your fit!\n" +
                "\n" +
                "The interior of the Super Tacks AS3 Skates are loaded with comfort and durability-enhancing features. The TotalDri liner with Durazone Abrasion Protection is an elite-performance moisture-wicking material that helps wick moisture away from the body, keeping them dry and light on their feet. The interior multi-density memory foam in the ankle areas wrap anatomically around players’ ankles keeping them comfortably locked into place. Lastly, the Injected Smooth Contour Pro comfort padding at the top of the skate boots is a one-piece injected tendon guard that hugs your foot and ankle for an even more responsive fit.\n" +
                "\n" +
                "Moving to the bottom half of the Super Tacks AS3 Skates, CCM utilizes their new SpeedBlade XS holder. These holders were developed as a quick and easy way for players to replace runners on the fly, while ensuring a consistent feel every time. The holders utilize a positive-lock mechanism that tightly secures the runners to the holder to prevent the blade from loosening during gameplay.\n" +
                "\n" +
                "Ready to take your game to the next level? Look no further than the CCM Super Tacks AS3 Senior Hockey Skates!");

        this.productDescription.put("CCM Super Tacks AS3 Pro Senior Ice Hockey Skates", "CCM Hockey is constantly pushing the boundaries when it comes to ice hockey skate innovation and this time is no different with the Super Tacks AS3 Pro Senior Hockey Skates! With the incorporation of the One-Piece boot, improved interior liner, and the SpeedBlade XS holders, the Super Tacks AS3 Pro Skates offer the complete package of ultimate performance, comfort, and efficiency.\n" +
                "\n" +
                "Starting with the fit, the Super Tacks AS3 Pro Skates feature the same tried and true anatomical fit that players are accustomed to with the Super Tacks line. This form-fitting, 360-degree fit around the forefoot, heel, and ankle helps eliminate negative space within the boot, allowing for more efficient energy transfer meaning improved speed and agility!\n" +
                "\n" +
                "Building upon that, the Super Tacks AS3 Pro Skates utilize CCM’s one-piece “Monoframe” quarter package. This one-piece build is the epitome of pure performance, built with a carbon-composite material that is both incredibly lightweight and durable. Additionally, heat maps show that, thanks to this setup, players’ feet sit 34% closer to the holder and use more of the skatebed. The result of this setup is superior energy transfer from the skating stride, to the feet, to the ice.\n" +
                "\n" +
                "Taking customization to another level is CCM’s incorporation of the XS Tongues on the Super Tacks AS3 Pro Skates. These easily-swappable skate tongues allow players to switch them out based on their personal preference of fit, feel, and protection. Furthermore, each one of the three tongue models are built with a D30 Smart Material that helps disperse the force of direct impacts, such as blocked shots, away from the sensitive ankle areas. Simply use the two clips at the base of the tongues to change them out and perfect your fit!\n" +
                "\n" +
                "The interior of the Super Tacks AS3 Pro Skates are loaded with comfort and durability-enhancing features. The TotalDri Pro+ liner with Durazone Abrasion Protection is an elite-performance moisture-wicking material that helps wick moisture away from the body, keeping them dry and light on their feet. The interior multi-density memory foam in the ankle areas wrap anatomically around players’ ankles keeping them comfortably locked into place. Lastly, the Injected Smooth Contour Pro comfort padding at the top of the skate boots is a one-piece injected tendon guard that hugs your foot and ankle for an even more responsive fit.\n" +
                "\n" +
                "Moving to the bottom half of the Super Tacks AS3 Pro Skates, CCM utilizes their newest holder and runner combination; the SpeedBlade XS holder paired with the XS1 Black runners. These holders were developed as a quick and easy way for players to replace runners on the fly, while ensuring a consistent feel every time. The holders utilize a positive-lock mechanism that tightly secures the runners to the holder to prevent the blade from loosening during gameplay.\n" +
                "\n" +
                "Paired with the SpeedBlade XS holders are the SpeedBlade XS1 Black Runners! These premium runners are oxide-treated, which allows the blade to remain harder and more durable than standard runners. This specialized coating will also help preserve edge life for a longer lasting sharpen. Additionally, these runners are 12% taller (+2mm) than standard runners, which helps improve agility and quickness!\n" +
                "\n" +
                "Ready to take your game to the next level? Look no further than the CCM Super Tacks AS3 Pro Senior Hockey Skates!");

        this.productDescription.put("CCM Super Tacks 9380 Senior Ice Hockey Skates", "CCM Hockey is constantly pushing the boundaries when it comes to ice hockey skate innovation and this time is no different with the Super Tacks 9380 Senior Hockey Skates! Designed and manufactured to cater to a wide range of players, the Super Tacks 9380 Skates offer the complete package of performance, comfort, and efficiency.\n" +
                "\n" +
                "Starting with the fit, the Super Tacks 9380 Skates feature the same tried and true anatomical fit that players are accustomed to with the Super Tacks line. This form-fitting, 360-degree fit around the forefoot, heel, and ankle helps eliminate negative space within the boot, allowing for more efficient energy transfer meaning improved speed and agility!\n" +
                "\n" +
                "Building upon that, the Super Tacks 9380 Skates utilize CCM’s Metaframe Technology quarter package. This ergonomic boot-shaping system provides a superior fit for your foot. This will take the players mind off of the skate so they can fully focus on the game and trust their skates to perform the way they need it.\n" +
                "\n" +
                "Taking customization to another level is CCM’s incorporation of the 10mm felt replaceable tongue with asymmetrical dual-density with embossed lace-bite protection allows for increased comfort and protection. Lastly, the Injected Smooth Contour Pro comfort padding at the top of the skate boots is a one-piece injected tendon guard that hugs your foot and ankle for an even more responsive fit.\n" +
                "\n" +
                "The interior of the Super Tacks 9380 Skates are loaded with comfort and durability-enhancing features. The TotalDri liner with Durazone Abrasion Protection is an elite-performance moisture-wicking material that helps wick moisture away from the body, keeping them dry and light on their feet. The interior multi-density memory foam in the ankle areas wrap anatomically around players’ ankles keeping them comfortably locked into place.\n" +
                "\n" +
                "Moving to the bottom half of the Super Tacks 9380 Skates, CCM utilizes their new SpeedBlade XS holder. These holders were developed as a quick and easy way for players to replace runners on the fly, while ensuring a consistent feel every time. The holders utilize a positive-lock mechanism that tightly secures the runners to the holder to prevent the blade from loosening during gameplay.\n" +
                "\n" +
                "Ready to take your game to the next level? Look no further than the CCM Super Tacks 9380 Senior Hockey Skates!");

        this.productDescription.put("CCM Jetspeed FT470 Senior Ice Hockey Skates", "Brand new for 2019 are the CCM JetSpeed FT470 Senior Hockey Skates! The CCM JetSpeed FT470 Skates build upon the concepts that made the JetSpeed line one of the best on the market by making key improvements to help maximize comfort, durability, quickness, and top speed!\n" +
                "\n" +
                "Jumping right into the boot, CCM has gone with a MetaFrame Technology infused quarter package. This lightweight composite boot features an ergonomic shape, which hugs the foot all the way around, ensuring that your heel stays locked in place to help generate maximum speed!\n" +
                "\n" +
                "The quarter package is constructed with CCM’s RocketFrame composite material. This NHL-caliber material is not only extremely lightweight but features elite levels of protection and durability, as well.\n" +
                "\n" +
                "CCM incorporated a 7mm felt tongue with molded Lace-bite Protection on their new JetSpeed FT470 Skates. This design provides ultimate protection and comfort and is paired with lace-bite protection that better protects high-wear areas for improved longevity!\n" +
                "\n" +
                "On the interior of the skates, the JetSpeed FT470 Hockey Skates feature CCM’s HD Microfiber liner. This lightweight material helps wick away any moisture, helping keep your feet dry and your skates light. For durability purposes, CCM included their DuraZone Abrasion Protection. These are abrasion patches that have been added to the interior of the boot where the eyelets are. These are high-wear areas, and by adding this feature, it greatly increases boot durability and comfort.\n" +
                "\n" +
                "Continuing on the comfort aspect, CCM again incorporated their smooth contour pro padding around the collar of the boot, which provides improved durability and superior foot and ankle comfort. This is partnered with multi-density memory foam in the ankle that wraps anatomically around your foot for a customized fit.\n" +
                "\n" +
                "Moving to the bottom half of the JetSpeed FT470 Hockey Skates is CCM’s brand new holder, known as the SpeedBlade XS holder! These holders were developed as a quick and easy way to replace blades on the fly, while ensuring you with a consistent feel and performance every time. The holders feature a positive-lock mechanism that tightly secures the blade to the holder to prevent the blade from loosening during gameplay. The consistently tightly-secured blade maximizes energy transfer from the boot to the holder and from the holder to the blade in every stride.\n" +
                "\n" +
                "If you are a player looking for mid-range skates designed for speed and comfort, look no further than the CCM JetSpeed FT470 Senior Ice Hockey Skates!");

        this.productDescription.put("CCM Jetspeed FT480 Senior Ice Hockey Skates", "Brand new for 2019 are the CCM JetSpeed FT480 Senior Hockey Skates! The CCM JetSpeed FT480 Skates build upon the concepts that made the JetSpeed line one of the best on the market by making key improvements to help maximize comfort, durability, quickness, and top speed!\n" +
                "\n" +
                "Jumping right into the boot, CCM has gone with a 3D-lasted, two-piece quarter package. This lightweight composite boot features an ergonomic shape, which hugs the foot all the way around, ensuring that your heel stays locked in place to help generate maximum speed!\n" +
                "\n" +
                "The quarter package is constructed with CCM’s RocketFrame composite material. This NHL-caliber material is not only extremely lightweight but features elite levels of protection and durability, as well.\n" +
                "\n" +
                "CCM incorporated a 7mm felt tongue with molded Lace-bite Protection on their new JetSpeed FT480 Skates. This design provides ultimate protection and comfort and is paired with lace-bite protection that better protects high-wear areas for improved longevity!\n" +
                "\n" +
                "On the interior of the skates, the JetSpeed FT480 Hockey Skates feature CCM’s best: the TotalDri Pro Liner. This lightweight material helps wick away any moisture, helping keep your feet dry and your skates light. For durability purposes, CCM included their DuraZone Abrasion Protection. These are abrasion patches that have been added to the interior of the boot where the eyelets are. These are high-wear areas, and by adding this feature, it greatly increases boot durability and comfort.\n" +
                "\n" +
                "Continuing on the comfort aspect, CCM again incorporated their smooth contour pro padding around the collar of the boot, which provides improved durability and superior foot and ankle comfort. This is partnered with multi-density memory foam in the ankle that wraps anatomically around your foot for a customized fit.\n" +
                "\n" +
                "Moving to the bottom half of the JetSpeed FT480 Hockey Skates is CCM’s brand new holder, known as the SpeedBlade XS holder! These holders were developed as a quick and easy way to replace blades on the fly, while ensuring you with a consistent feel and performance every time. The holders feature a positive-lock mechanism that tightly secures the blade to the holder to prevent the blade from loosening during gameplay. The consistently tightly-secured blade maximizes energy transfer from the boot to the holder and from the holder to the blade in every stride.\n" +
                "\n" +
                "If you are a high-level hockey player looking for speed and comfort, look no further than the CCM JetSpeed FT480 Senior Ice Hockey Skates!");

        this.productDescription.put("CCM RibCor 78K Senior Ice Hockey Skates", "Brand new for the 2019-2020 are CCM’s latest iteration of the RibCor skate line, the CCM RibCor 78K Senior Hockey Skates! With a refined design, along with the addition of a few key features, the CCM RibCor 78K Hockey Skates look to give players the perfect balance of comfort, fit, and performance.\n" +
                "\n" +
                "Starting off with the quarter package, the RibCor 78K Hockey Skates feature a composite quarter package. This 3D-lasted material features great forward flex and lateral stability, allowing players to get the most power and efficiency out of their strides. The FlexFrame Technology within the boot provides a contoured and snug fit all around the foot. Furthermore, this technology gives the boot a variable stiffness profile offering an excellent fit in any situation.\n" +
                "\n" +
                "Utilized on the RibCor 78K Hockey Skates is a 7mm felt tongue with molded lace-bite protection. This multi-layered, 7mm black felt tongue provides players with great protection and comfort. Additionally, the tongue features injected lace-bite protection for increased comfort and added durability.\n" +
                "\n" +
                "Onto the interior, the RibCor 78K Hockey Skates feature a TotalDri Pro Liner with Durazone abrasion protection! This high-performance moisture wicking material works to keep players as dry and comfortable as possible all game long. Additionally, the Durazone abrasion protection liner has improved durability in high wear areas to keep from wearing down as quickly. Going hand in hand with this liner is the ADPT memory foams. These symmetrical foams wrap snugly but comfortably around players feet, providing an anatomical type fit and comfort for all different foot shapes.\n" +
                "\n" +
                "Moving to the lower half of the RibCor 78K Hockey Skates is where you will find the brand new SpeedBlade XS holder! On the back of each holder, players will find what is known as CCM’s BladeLock Technology. This is a positive-lock dial that tightly secures the blade to the holder and helps prevent the blade from loosening during gameplay. In turn, this design provides players with consistent and maximized energy transfer within each stride.\n" +
                "\n" +
                "With the improvised FlexFrame Technology, along with the addition of the new holder and runner combination, the CCM RibCor 78K Senior Hockey Skates further enhance protection, comfort and performance!");

        /**
         * Graf
         */
        this.productDescription.put("Graf G755 Pro Senior Ice Hockey Skates", "Features 2-piece heat moldable PE reinforced microfiber quarter panels with traditional stitched construction\n" +
                "Stiff tendon guard for max support\n" +
                "Tongue features 10mm protective felt to help prevent lace bite\n" +
                "Multi-density liner locks the heel into place for maximized energy transfer\n" +
                "Molded arch support with brushed microfiber foot bed\n" +
                "Pre-molded internal liner with asymmetrical molded ankle pockets and heel pocket\n" +
                "4K composite outsole\n" +
                "Cobra 2.0 SS stainless steel runner with 10ft radius\n" +
                "Graf 5000 Holder with replaceable runner mounted");

        this.productDescription.put("Graf PK3900 Senior Ice Hockey Skates", "New for 2020 Graf has returned to the skate market with the Graf PK3900 Senior Ice Hockey Skates. Looking to provide players with a skate that has a classic Graf feel mixed with modern technology, the Graf PK3900 is a great choice for any skater.\n" +
                "\n" +
                "Graf skates are legendary for their comfort, performance, and quality. Now Manufactured and distributed by Vaughn Custom Sports, Graf is looking to continue with their tradition of high-quality products and innovation that only the expertise and craftsmanship that is designed in each Graf skate can provide.\n" +
                "\n" +
                "Starting with the boot, the Graf PK3900 Skates are using 2-piece injection molded quarter panels with carbon look exterior. This 2-piece quarter panel construction will provide a secure fit and offer good stability and protection.\n" +
                "\n" +
                "Moving to the front of the skate, The Graf PK3900 Skates will include an asymmetrical tongue built specifically to stay locked in position and prevent shifting during play. This felt tongue will be comfortable without sacrificing protection. Down at the toe, Graf has lined their toe cap to better help protect your foot from impacts while providing extra durability to the skate.\n" +
                "\n" +
                "Up at the ankle, the Graf PK3900 Skates will have shaped ankle pockets that will help with a solid fit around the ankle allowing for greater control and fit. Down inside the skate, a heat moldable 3-D heel lock design will help to lock your foot in place for a great fit and comfort level. A stay dry liner throughout the interior of the boot helps to wick away moisture and keep your foot dry. For the footbed, the Graf PK3900 will come with a molded arch support brushed microfiber foot bed designed to help with energy transfer and comfort.\n" +
                "\n" +
                "Finally, down at the bottom of the skate, the Graf PK3900 Skates will come with a Graf 3500 holder with replaceable Cobra 2.0 Stainless Steel runners. With a 10ft radius, these runners allow for tighter and quicker turns.\n" +
                "\n" +
                "If you are a mid-level skater, looking for a classic feel and fit, mixed with today’s skate technology, look no further than the Graf PK3900 Senior Ice Hockey Skates.");

        this.productDescription.put("Graf PK7900 Senior Ice Hockey Skates", "New for 2020 Graf has returned to the skate market with the Graf PK7900 Senior Ice Hockey Skates. Looking to provide players with a skate that has a classic Graf feel mixed with modern technology, the Graf PK7900 is a great choice for any skater.\n" +
                "\n" +
                "Graf skates are legendary for their comfort, performance, and quality. Now Manufactured and distributed by Vaughn Custom Sports, Graf is looking to continue with their tradition of high-quality products and innovation that only the expertise and craftsmanship that is designed in each Graf skate can provide.\n" +
                "\n" +
                "Starting with the boot, the Graf PK7900 Skates are using new ultra-light 2-piece heat moldable Pure composite quarter panels. This 2-piece quarter panel construction will provide unmatched heel lock and fit. With their SPEEDFIT technology, these skates are designed for quick break-in, comfort, support, and precision control. SPEEDFIT maximizes heel lock, stiffness, and increases protection without adding additional weight to the skate.\n" +
                "\n" +
                "Moving to the front of the skate, The Graf PK7900 Skates will include an asymmetrical tongue built specifically to stay locked in position and prevent shifting during play. This felt tongue will be comfortable without sacrificing protection. Down at the toe, Graf has lined their toe cap with a foam lining that will better help protect your foot from impacts while providing extra durability to the skate.\n" +
                "\n" +
                "Up at the ankle, the Graf PK7900 Skates will have a FlexFit collar that will give unmatched agility in tight corners. Adding to that are the Asymmetrical ankle pockets that will help with a precise fit around the ankle. This allows for maximum stability and fit around the ankle.\n" +
                "\n" +
                "The Graf PK7900 Skate will have an overall contoured shape to help reduce negative space inside the skate allowing for that precise improved fit. A brushed nylon quick dry liner throughout the inside will help to wick moisture away, keeping the foot dry, and add a great level of comfort for your foot.\n" +
                "\n" +
                "For the footbed, the Graf PK7900 will come with a stock molded arch support brushed microfiber foot bed. For added comfort and fit, they are including a pair of Orthotic heat moldable footbeds in the box. This foot bed will be 3D shaped for a precision fit, have added foot stability, a heel lock design to help secure your foot, and shock inserts at the heel and forefoot to reduce vibrations.\n" +
                "\n" +
                "Finally, down at the bottom of the skate, the composite mid and outsole will help to give added boot strength and superior blade control for maximum energy transfer. They are pairing that with the Graf 5500 Holder and replaceable Cobra 3.0 stainless steel runners with a 10ft. radius, allowing for tighter and quicker turns.\n" +
                "\n" +
                "If you are a high-level skater, looking for a classic feel and fit, mixed with the best of today’s skate technology, look no further than the Graf PK7900 Senior Ice Hockey Skates.");

        /**
         * True
         */
        this.productDescription.put("True TF7 Senior Ice Hockey Skates", "New for the 2020 season, True is looking to transform the skate market once again, introducing its groundbreaking technology in the performance and competitive stock skate categories with the True TF7 Senior Ice Hockey Skates. Built around the fundamental player benefits that have become synonymous with true: best-in-class fit, best-in-class performance and versatile designs.\n" +
                "\n" +
                "Starting with the boot, the True TF7 Senior Ice Hockey Skates are a culmination of thousands of foot scans and hundreds of hours of development to get as close to a custom skate fit as possible, in a stock skate. The True TF7 shell uses TRUESHELL tech, their proprietary fiberglass one-piece shell that creates the thermoformed boot and gives it that best-in-class fit. Moving down the boot the TRUESHELL tech wraps the toe box providing superior protection while eliminating any weak points around high impact areas.\n" +
                "\n" +
                "Moving to the top of the skate the True TF7 Senior skates feature an anatomical felt tongue that creates a stiff, contoured design. This traditional feel and fit allows the tongue to provide enhanced comfort for any skater.\n" +
                "\n" +
                "For the interior of the skate, True went with a blackwick liner throughout the inside of the boot. This antimicrobial, moisture wicking liner helps to keep your feet dry while simultaneously providing enhanced comfort. True has also Implemented a personalized fit system with the insole. The TF7 insole integrates fit inserts to accommodate low, mid, and high arch feet. Placement of the fit inserts are marked on the insole for easy positioning to ensure proper fit regardless of your arch type.\n" +
                "\n" +
                "Jumping down to the bottom of the True TF7 Senior skates, True is using its all-new versatile SHIFT Holder. The SHIFT holders feature enhanced BenchChange technology allowing for faster blade replacement without the need for a special tool. Simply pull the blue slider back, pop the blade out, and replace with the replacement runner. These holders are specifically designed to keep you from missing critical time on the ice. The True TF7 skates will also use SHIFT stainless steel runners. The stainless-steel runner provides a dense, durable quality stainless steel that will hold a sharp edge over time.\n" +
                "\n" +
                "The True TF7 Senior Ice Hockey Skate will be thermoformable to further enhance the fit and comfort of the skate and make sure that your foot has the best fit inside the boot as possible.\n" +
                "\n" +
                "If you are a mid-level skater looking for a custom-like fit and dynamic performance, look no further than the True TF7 Senior Ice Hockey Skates.");

        /**
         * A&R
         */
        this.productDescription.put("A&R Bob Training Skates", "Bob Skates are double runner, adjustable plastic skates with steel blades for beginners. Ages 2-5.\n" +
                "Adjusts to fit child sizes 6-13\n" +
                "Solid construction\n" +
                "Zinc-plated blades");
    }

    private void juniorIceHockeySkates()
    {
        this.productDescription.put("Bauer Nexus 2N Junior Ice Hockey Skates", "New for the 2018 season are the Bauer Nexus 2N Junior Ice Hockey Skates. Bauer looks to follow suit of the highly popular Nexus line by adding a few key upgrades to this current iteration, further enhancing an already highly regarded skate.\n" +
                "\n" +
                "For the 2N skates, Bauer focused on comfort and durability. With this in mind, they have introduced their new 3D lasted carbon Curv composite quarter package. With this setup being more responsive to heat molding than ever before, it will provide an anatomical, close to the foot feel, making your strides on the ice even more efficient. Not only is this setup highly protective and durable, but it is also comfortable on your feet!\n" +
                "\n" +
                "This goes hand in hand with the introduction of Bauer’s Form-Fit tongue. This tongue is a 2-piece, 48-ounce felt tongue with a reinforced metatarsal guard giving you the perfect blend of comfort and protection.\n" +
                "\n" +
                "Moving on to the interior of the skate, Bauer has gone with their new Hydra Max 2 liner. This material is highly durable, does a great job of wicking away moisture, and due to the soft touch, is perfect for players who prefer not to wear socks while playing!\n" +
                "\n" +
                "This liner is paired with Bauer’s Form-Fit+ footbed. This footbed features a tacky grip in the heel and toe helping keep the foot firmly planted in the skate. This concept helps to prevent any power loss in your strides while skating.\n" +
                "\n" +
                "The Bauer Nexus 2N Junior Ice Hockey Skates feature the patented LightSpeed EDGE holder. These holders give you the option to quickly switch out a runner on the fly, if one were to break.\n" +
                "\n" +
                "If you are an elite-level player looking for a skate whose performance will match yours, look no further than the Bauer Nexus 2N Junior Ice Hockey Skates!");

        this.productDescription.put("Bauer Supreme S29 Junior Ice Hockey Skates", "New for 2019 are the Bauer Supreme S29 Junior Ice Hockey Skates! Built with an anatomical fit, featuring some high-end technology, the Supreme S29 Hockey Skates are a great option at an even better price point.\n" +
                "\n" +
                "Jumping right into it, the quarter package of the Supreme S29 Hockey Skates is made up of a 3D fiber composite+ material. This new setup is very lightweight, keeping players mobile on their skates, but also highly protective against a wide array of in-game impacts such as blocked shots, stick checks, and so on.\n" +
                "\n" +
                "The tongue is a 2-piece design, utilizing 48oz felt materials and a high-density reinforced metatarsal guard, providing players with great comfort and protection.\n" +
                "\n" +
                "Moving to the interior of the Supreme S29 Hockey Skates, they feature a Hydrophobic microfiber liner that helps wick moisture away from the foot, ensuring as little slippage as possible while the skates are in use. This translates to more energy transferred directly from your skates to the ice within every skating stride. The ankles are well protected too, thanks to the lightweight memory foam pads!\n" +
                "\n" +
                "Helping keep your foot locked into place are the Form-fit+ footbeds. These footbeds feature a grippy material on them, to really keep your feet stable while making hard cuts.\n" +
                "\n" +
                "To conclude, another upgrade found within the Supreme S29 Hockey Skates is the new Fiber Composite outsole. This outsole will help improve skating stability during hard acceleration and turning, while remaining very lightweight.\n" +
                "\n" +
                "If you are a high-level hockey player looking for a pair of skates that feature high-end specs, but don’t want to break the bank, check out the Bauer Supreme S29 Junior Ice Hockey Skates!");

        this.productDescription.put("True TF7 Junior Ice Hockey Skates", "New for the 2020 season, True is looking to transform the skate market once again, introducing its groundbreaking technology in the performance and competitive stock skate categories with the True TF7 Junior Ice Hockey Skates. Built around the fundamental player benefits that have become synonymous with true: best-in-class fit, best-in-class performance and versatile designs.\n" +
                "\n" +
                "Starting with the boot, the True TF7 Junior Ice Hockey Skates are a culmination of thousands of foot scans and hundreds of hours of development to get as close to a custom skate fit as possible, in a stock skate. The True TF7 shell uses TRUESHELL tech, their proprietary fiberglass one-piece shell that creates the thermoformed boot and gives it that best-in-class fit. Moving down the boot the TRUESHELL tech wraps the toe box providing superior protection while eliminating any weak points around high impact areas.\n" +
                "\n" +
                "Moving to the top of the skate the True TF7 Junior skates feature an anatomical felt tongue that creates a stiff, contoured design. This traditional feel and fit allows the tongue to provide enhanced comfort for any skater.\n" +
                "\n" +
                "For the interior of the skate, True went with a blackwick liner throughout the inside of the boot. This antimicrobial, moisture wicking liner helps to keep your feet dry while simultaneously providing enhanced comfort. True has also Implemented a personalized fit system with the TF7 insole. This insole integrates fit inserts to accommodate low, mid, and high arch feet. Placement of the fit inserts are marked on the insole for easy positioning to ensure proper fit regardless of your arch type.\n" +
                "\n" +
                "Jumping down to the bottom of the True TF7 Junior skates, True is using its all-new versatile SHIFT Holder. The SHIFT holders feature enhanced BenchChange technology allowing for faster blade replacement without the need for a special tool. Simply pull the blue slider back, pop the blade out, and replace with the replacement runner. These holders are specifically designed to keep you from missing critical time on the ice. The True TF7 skates will also use SHIFT stainless steel runners. The stainless-steel runner provides a dense, durable quality stainless steel that will hold a sharp edge over time.\n" +
                "\n" +
                "The True TF7 Junior Ice Hockey Skate will be thermoformable to further enhance the fit and comfort of the skate and make sure that your foot has the best fit inside the boot as possible.\n" +
                "\n" +
                "If you are a mid-level skater looking for a custom like fit and dynamic performance, look no further than the True TF7 Junior Ice Hockey Skates.v");

        this.productDescription.put("Graf PeakSpeed PK3300 Junior Ice Hockey Skates", "Quarter: 2-piece heat moldable composite quarter panel\n" +
                "Tongue: Asymmetrical design prevents shifting of the tongue for a perfect fit all game long\n" +
                "Runner: GRAF Attack stainless steel runner\n" +
                "Toe Cap: Lined toe cap provides enhanced impact protection\n" +
                "Liner: Heat-moldable, heel-locking, stay dry liner\n" +
                "Holder:GRAF Attack holder with replaceable runner\n" +
                "Footbed: Moldable arch support with brushed microfiber for maximum comfort");

        this.productDescription.put("CCM Super Tacks AS3 Pro Intermediate Ice Hockey Skates", "CCM Hockey is constantly pushing the boundaries when it comes to ice hockey skate innovation and this time is no different with the Super Tacks AS3 Pro Intermediate Hockey Skates! With the incorporation of the One-Piece boot, improved interior liner, and the SpeedBlade XS holders, the Super Tacks AS3 Pro Skates offer the complete package of ultimate performance, comfort, and efficiency.\n" +
                "\n" +
                "Starting with the fit, the Super Tacks AS3 Pro Skates feature the same tried and true anatomical fit that players are accustomed to with the Super Tacks line. This form-fitting, 360-degree fit around the forefoot, heel, and ankle helps eliminate negative space within the boot, allowing for more efficient energy transfer meaning improved speed and agility!\n" +
                "\n" +
                "Building upon that, the Super Tacks AS3 Pro Skates utilize CCM’s one-piece “Monoframe” quarter package. This one-piece build is the epitome of pure performance, built with a carbon-composite material that is both incredibly lightweight and durable. Additionally, heat maps show that, thanks to this setup, players’ feet sit 34% closer to the holder and use more of the skatebed. The result of this setup is superior energy transfer from the skating stride, to the feet, to the ice.\n" +
                "\n" +
                "Taking customization to another level is CCM’s incorporation of the XS Tongues on the Super Tacks AS3 Pro Skates. These easily-swappable skate tongues allow players to switch them out based on their personal preference of fit, feel, and protection. Furthermore, each one of the three tongue models are built with a D30 Smart Material that helps disperse the force of direct impacts, such as blocked shots, away from the sensitive ankle areas. Simply use the two clips at the base of the tongues to change them out and perfect your fit!\n" +
                "\n" +
                "The interior of the Super Tacks AS3 Pro Skates are loaded with comfort and durability-enhancing features. The TotalDri Pro+ liner with Durazone Abrasion Protection is an elite-performance moisture-wicking material that helps wick moisture away from the body, keeping them dry and light on their feet. The interior multi-density memory foam in the ankle areas wrap anatomically around players’ ankles keeping them comfortably locked into place. Lastly, the Injected Smooth Contour Pro comfort padding at the top of the skate boots is a one-piece injected tendon guard that hugs your foot and ankle for an even more responsive fit.\n" +
                "\n" +
                "Moving to the bottom half of the Super Tacks AS3 Pro Skates, CCM utilizes their newest holder and runner combination; the SpeedBlade XS holder paired with the XS1 Black runners. These holders were developed as a quick and easy way for players to replace runners on the fly, while ensuring a consistent feel every time. The holders utilize a positive-lock mechanism that tightly secures the runners to the holder to prevent the blade from loosening during gameplay.\n" +
                "\n" +
                "Paired with the SpeedBlade XS holders are the SpeedBlade XS1 Black Runners! These premium runners are oxide-treated, which allows the blade to remain harder and more durable than standard runners. This specialized coating will also help preserve edge life for a longer lasting sharpen. Additionally, these runners are 12% taller (+2mm) than standard runners, which helps improve agility and quickness!\n" +
                "\n" +
                "Ready to take your game to the next level? Look no further than the CCM Super Tacks AS3 Pro Intermediate Hockey Skates!");
    }

    private void youthIceHockeySkates()
    {
        this.productDescription.put("CCM Super Tacks 9350 Youth Ice Hockey Skates", "The CCM Super Tacks 9350 Youth Hockey Skates are a great option for youth hockey players who are still developing their game. Built with reinforced injected materials in the quarter package, the 9380 Youth Hockey Skates provide great protection and just the right amount of stiffness. This will allow younger players to hone their skating skills without having to worry about any discomfort!\n" +
                "\n" +
                "The microfiber liner on the interior of the 9350 Youth Hockey Skates is comfortable, durable, and will help dry the skates out quicker while not in use.\n" +
                "\n" +
                "The 5mm felt tongue with reinforcement layers adds another layer of comfort and protection against a wide variety of impacts like deflected shots or stick whacks.\n" +
                "\n" +
                "Lastly, the 9350 Youth Hockey Skates utilize CCM’s SpeedBlade holder and runners. The back of the holder is slightly elevated for an increased angle of attack and improved straight-line speed.\n" +
                "\n" +
                "Get the best fit for tiny feet with the CCM Super Tacks 9350 Youth Hockey Skates!");

        this.productDescription.put("CCM Jetspeed FT2 Youth Ice Hockey Skates", "Brand new for 2019 are the CCM JetSpeed FT2 Youth Hockey Skates! The CCM JetSpeed FT2 Skates build upon the concepts that made the FT1’s one of the top skates on the market, with key improvements that help maximize comfort, durability, quickness, and top speed!\n" +
                "\n" +
                "Jumping right into the boot, CCM has gone with their ever-popular one-piece quarter package, but this generation has been further evolutionized, now known as the LiteFrame 360 EVO technology. This boot was engineered with no outsole, no stitching, and no glue in high-energy transfer zones. The result of this is a tapered, 360-degree fit, providing directly energy transfer for explosive speed and power in every stride. Additionally, the boot is super lightweight and highly rigid, generating ultimate skating performance.\n" +
                "\n" +
                "The LiteFrame 360 EVO Quarter Package is constructed with CCM’s RocketFrame composite material. This NHL-caliber material is not only extremely lightweight but features elite levels of protection and durability, as well.\n" +
                "\n" +
                "Brand new for the JetSpeed FT2 Hockey Skates is CCM’s new Metamonic Tongue. This new design is lighter than ever before and features an asymmetrical flex point that fully wraps around your foot to enhance fit, feel, and flex. This is especially important when looking to get the most speed and quickness out of your strides. Additionally, they are still constructed of high-density felt that provides you with pro levels of comfort and protection.\n" +
                "\n" +
                "On the interior of the skates, the JetSpeed FT2 Hockey Skates feature CCM’s best: the TotalDri Pro Liner. This lightweight material helps wick away any moisture, helping keep your feet dry and your skates light. For durability purposes, CCM included their DuraZone Abrasion Protection. These are abrasion patches that have been added to the interior of the boot where the eyelets are. These are high-wear areas, and by adding this feature, boot durability and comfort is greatly increased!\n" +
                "\n" +
                "Continuing on the comfort aspect, CCM again incorporated their smooth contour pro padding around the collar of the boot, which provides improved durability and superior foot and ankle comfort. This is partnered with multi-density memory foam in the ankle that wraps anatomically around your foot for a customized fit.\n" +
                "\n" +
                "Moving to the bottom half of the JetSpeed FT2 Hockey Skates is CCM’s brand new holder, known as the SpeedBlade XS holder! These holders were developed as a quick and easy way to replace blades on the fly, while ensuring you a consistent feel and performance every time. The holders feature a positive-lock mechanism that tightly secures the blade to the holder to prevent the blade from loosening during gameplay. The consistently tightly-secured blade maximizes energy transfer from the boot to the holder and from the holder to the blade in every stride.\n" +
                "\n" +
                "Partnered with the new SpeedBlade XS holder are the new SpeedBlade XS1 Black Runners! These runners are oxide-treated, which is harder than traditional steel and will help preserve edge life. Additionally, these runners are 12% taller (+2mm) than standard runners, which helps improve agility and performance!\n" +
                "\n" +
                "With the revamped one-piece boot, the newly designed tongue, and the new holder and runner combination, these skates have been built with speed, comfort, and performance in mind. Leave the competition behind with the CCM JetSpeed FT2 Youth Hockey Skates!");

        this.productDescription.put("CCM Super Tacks AS1 Youth Ice Hockey Skates", "We're so sure that you'll love your new CCM Super Tacks AS1 Ice Hockey Skates that if you aren't satisfied for any reason, we'll take them back within 90 days of purchase and issue a full refund - no questions asked. This offer is valid on qualifying CCM Super Tacks skates purchased between April 28th, 2018 and October 1st, 2018. Customers who wish to return their skates need to call customer service to initiate a standard merchandise return within the 90 day window. Upon receipt and inspection of the skates, the customer will be provided a refund for the full purchase amount of the skates. Please allow up to 7 days for us to process your return and issue a refund.\n" +
                "\n" +
                "For 2018, CCM builds off their successful Tacks line of skates and offers key upgrades to the CCM Super Tacks AS1 Youth Ice Hockey Skate.\n" +
                "\n" +
                "CCM’s Super Tacks AS1 Youth Ice Hockey Skates offer the comfort and support that help younger skaters develop. The AS1 Youth skates will be constructed using an injected synthetic composite, that will provide the right amount of stiffness for developing skaters, but also have some flex to allow them to develop their strength.\n" +
                "\n" +
                "Internally the HD padding will provide a wrapping comfort while providing support needed for smaller skaters. Coating the padding will be a microfiber liner meant to wick away moisture to keep the skaters feet dry and locked in place.\n" +
                "\n" +
                "Rounding out the skate will be The Speedblade Pro holder and runner combo. This one-piece combo gives an increased attach angle allowing for proper skating mechanics. The stainless-steel runner provides longer edge life and increased performance.\n" +
                "\n" +
                "Younger skaters looking for a higher performance skate should check out the CCM Super Tacks AS1 Youth Ice Hockey Skate.");

        this.productDescription.put("Bauer Vapor X2.7 Youth Ice Hockey Skates", "Built with the new Comfort Flex System, the Bauer Vapor X2.7 Youth Ice Hockey Skates feature great comfort as soon as you put your foot into the skates, increased visual aesthetics, and additional flex within the boot. The one-piece 3-D Polycarb quarter construction provides players with added comfort and flex, helping the boot form to the natural shape of players’ feet.\n" +
                "\n" +
                "The tongue is constructed of Bauer’s 30-ounce tongue with HD injected metatarsal guards. This setup provides players with a highly comfortable and protective fit and feel!\n" +
                "\n" +
                "Moving to the interior of the Vapor X2.7 Hockey Skates, they feature a microfiber liner. This liner is specifically designed to keep players feet dry and comfortable by working to wick away moisture. Paired with this liner are the memory foam pads within the ankles of the boot. This foam delivers premium thermoformability, great comfort and durability, and an improved fit!\n" +
                "\n" +
                "The lower half of the Vapor X2.7 Hockey Skates feature Bauer’s LightSpeed PRO II holders, paired with stainless steel runners. This setup is highly durable and lightweight, and thanks to the stainless-steel runners, provide increased longevity on skate sharpenings.\n" +
                "\n" +
                "With the additions of the Comfort Flex system, and the one-piece Polycarb quarter package, every element of the Bauer Vapor X2.7 Youth Ice Hockey Skates offers high-end performance and a quality feel every time you put them on your feet!");

        this.productDescription.put("Bauer Supreme 3S Youth Ice Hockey Skates", "New for the 2020 season are the Bauer Supreme 3S Youth Ice Hockey Skates. Bauer looks to follow a suit of the highly successful Supreme line by adding a few key upgrades to this current iteration, making them a great choice for any skater.\n" +
                "\n" +
                "Starting off with the Quarter Package of the 3S skates, a fiber composite construction with provides protection, durability, and comfort. Down at the toe, a regular toe design provides protection to the player.\n" +
                "\n" +
                "Around on the tongue of the 3S, a Classic Pro 30oz Felt tongue is designed to give the player comfort when leaning forward in their skates while staying sturdy over high amounts of use. Moving down, the ankle padding is made up of Lightweight Memory Foam solely for comfort of the player in this skate.\n" +
                "\n" +
                "Moving down to the interior of the 3S, a Die Cut EVA footbed designed to provide the goaltender with optimal comfort and prevent moisture build up. Flipping around to the other side, the outsole is made up of TPR which gives a sturdy base to this skate for great energy transfer.\n" +
                "\n" +
                "Finally, the blade holder is Bauer’s TUUK Lightspeed which does not include the blade trigger as seen in higher level Bauer Skate. The runner is the LS1 with power Profile which a great mid-level steel seen in many Bauer skates.\n" +
                "\n" +
                "If you are looking for a reliable Bauer skate built to keep you well protected and highly comfortable, look no further than the Bauer Supreme 3S Youth Ice Hockey Skates.");
    }

    private void accessories()
    {
        /**
         * Pucks
         */
        this.productDescription.put("NHL Official Black Ice Hockey Puck", "NHL Official Black Ice Hockey Puck");

        this.productDescription.put("Bauer Standard Ice Hockey Puck", "Standard ice hockey puck\n" +
                "Black");

        this.productDescription.put("A&R 4oz. Ice Hockey Mite Puck", "Manufactured in the Czech Republic, A&R’s pucks are made of vulcanized rubber and adhere to the strictest standards of hardness, weight, rebound elasticity, flatness, surface quality, diameter and thickness corresponding to International, Mite League and Canadian-American National Ice-Hockey Leagues.");

        this.productDescription.put("Winnwell Ice Hockey Puck Pail - 18 Pack", "Pail of 18 Hockey Pucks\n" +
                "Solves storage needs\n" +
                "Convenient easy to carry pail\n" +
                "Total package weight 108 oz\n" +
                "Helps keep pucks together");

        /**
         * Tape
         */
        this.productDescription.put("Renfrew Clear Shinpad Hockey Tape - 3 Pack", "Ideal for securing and stabilizing protective shin guards.\n" +
                "This higly comfortable tape is formulated to provide sufficient elasticity to hold shin guards, or other equipment securely in place, yet allow players to skate comfortably through the game.\n" +
                "The product is water resistant and is available in clear to match any uniform.\n" +
                "1 roll = 1 in x 98 ft (24mm x 30m)\n" +
                "Sold in 3 Pack");

        this.productDescription.put("Renfrew Black Cloth Hockey Tape - 3 Pack", "Woven cotton cloth tape\n" +
                "Can be used for taping blades, grips, handles and equipment\n" +
                "Thicker, more consistent coating of adhesive creates a moisture barrier between the ice and your stick\n" +
                "Roll Length: 1 inch x 27 yards (24mm x 25m)\n" +
                "Sold in 3 Pack");

        this.productDescription.put("Renfrew White Cloth Hockey Tape - 3 Pack", "Woven cotton cloth tape\n" +
                "Can be used for taping blades, grips, handles and equipment\n" +
                "Thicker, more consistent coating of adhesive creates a moisture barrier between the ice and your stick\n" +
                "Roll Length: 1 inch x 27 yards (24mm x 25m)\n" +
                "Sold in 3 Pack");

        /**
         * Net
         */
        this.productDescription.put("Winnwell Pro Form 72in. Regulation Hockey Net w/ QuickNet Mesh System", "QuickNet system:\n" +
                "Compared to a traditional lace up mesh system, QuickNet drastically reduces installation time by using built in heavy duty velcro tabs that wrap around the \"ROADEZ\" wire wield system in 3 easy steps\n" +
                "High-tensile 2 inch (5.08cm) steel tubing (front posts) and 1.5 inch (3.81cm) steel tubing (back posts)\n" +
                "Goal dimensions - 72in. x 48in. x 30in. (1.82m x 1.22m x 0.76m)\n" +
                "Full-wire \"ROADEZ\" weld for comprehensive mesh protection\n" +
                "QuickNet Mesh System outperforms traditional net installation\n" +
                "Heavy duty 18,000D polyester mesh netting\n" +
                "No tools required - quick and secure\n" +
                "Padded center post cover\n" +
                "Not made for ice pucks");

        this.productDescription.put("Winnwell 60in. Hockey Net w/ QuickNet Mesh System", "QuickNet system:\n" +
                "\n" +
                "Compared to a traditional lace up mesh system, QuickNet drastically reduces installation time by using built in heavy duty velcro tabs that wrap around the \"ROADEZ\" wire wield system in 3 easy steps\n" +
                "\n" +
                "Goal dimensions - 60in. x 44in. x 24in. (1.52m x 1.12m x 0.61m)\n" +
                "Full-wire \"ROADEZ\" weld for comprehensive mesh protection\n" +
                "QuickNet Mesh System outperforms traditional net installation\n" +
                "Heavy duty 10,000D polyester mesh netting\n" +
                "High-tensile 1.25 in. (3.20 cm) steel tubing\n" +
                "No tools required - quick and secure\n" +
                "60 in. net\n" +
                "Not made for ice pucks");

        this.productDescription.put("WinnWell 54in. Hockey Net w/ QuikNet Mesh System", "WinnWell's 54in. net is perfect for players looking for a steel net that is lightweight and easy to transport. The patent pending QuikNet mesh system reduces the setup time significantly. This set includes 1\" steel posts.\n" +
                "\n" +
                "Goal dimensions: 54in x 44 in x 20in (1.37m x 1.12m x 0.51m)\n" +
                "High-tensile 1.0 inch (2.54 cm) steel tubing\n" +
                "Heavy duty 8,000D polyester mesh netting\n" +
                "No tools required - quick and secure\n" +
                "Full-wire \"ROADEZ\" weld for compregensive mesh protection\n" +
                "QuikNET Mesh System outperforms traditional net installation");

        this.productDescription.put("Elite PRO-X7 Wide Moulded Tip Laces", "Unwaxed\n" +
                "Moulded Tip\n" +
                "Lightweight");

        /**
         * Lace
         */
        this.productDescription.put("Elite WAXED Molded Tip Laces", "Stiff, Lightweight, Agile and all out performance\n" +
                "The \"elite hockey\" laces are designed for players with fast feet and quick moves\n" +
                "Our waxed molded tip hockey laces are revolutionary and light years ahead in quality than any other hockey lace on the planet\n" +
                "The \"elite hockey\" laces are packaged in our innovative hockey lace boxes and are every hockey players dream come true");

        this.productDescription.put("Elite Pro S700 WAXED Molded Tip Laces", "Stiff, lightweight design\n" +
                "Waxed for a sturdy, reliable fit\n" +
                "Molded tips\n" +
                "Sold in pairs");

        this.productDescription.put("Elite Pro-Series Premium Wide NON-WAXED Molded Tip Laces", "Non-waxed\n" +
                "Moulded Tip\n" +
                "Lightweight\n" +
                "Wide lace");

        /**
         * Sticks
         */
        this.productDescription.put("Warrior Alpha QX Grip Senior Hockey Stick", "New for the 2017 season is Warrior's Alpha QX Grip Senior Hockey Stick! This stick is a completely new addition to the Warrior stick line and features upgraded performance and technology that has yet to be seen in a Warrior stick!\n" +
                "\n" +
                "The Warrior Alpha QX Stick was built with quick shot release and deadly accuracy in mind. Taking this into consideration, Warrior introduces the Sabre Taper. This taper allows the stick to maintain stiffness during shooting, which allows for a quicker release and a stiffer blade feel giving you a more accurate shot!\n" +
                "\n" +
                "This new Sabre Taper goes hand in hand with Warrior's new Quick Strike Technology. This is a low to mid-kick point profile that allows the stick to flex from your bottom hand all the way through the lower third of the shaft. This technology allows you to effortless load up your shots and get them off your blade as quickly as possible.\n" +
                "\n" +
                "The Warrior Alpha QX Stick also features the True 1 Phantom Feel construction process, making it a true one-piece stick. The shaft itself is constructed of 1000 Minimus Carbon materials, giving it a lightweight feel (410 grams), but also allows the stick to remain durable and always game ready.\n" +
                "\n" +
                "Thanks to the redesigned taper and repositioned flex point, this is Warrior's most explosive stick to date! If you are an elite-level player looking for a stick to help you be a dominant force on the ice, look no further than the Warrior Alpha QX Grip Senior Hockey Stick!");

        this.productDescription.put("Warrior Covert QRE SuperLight Grip Senior Hockey Stick", "New for the 2018 season Warrior Covert QRE SL Grip Senior Hockey Stick. With its reduced weight and, appearance redesigned, and an adjusted taper, this is Warrior’s most advanced stick to date optimized for a lightning-quick shot release.\n" +
                "\n" +
                "The SL in the name stands for Super Light, which is fitting, as this stick weighs in at a very impressive 400 grams. Along with being lighter than its predecessor, the Warrior Covert QRE SL Grip Senior Hockey Stick has changed appearance wise as well. Although the design and graphics are the same as the Covert QREdge, Warrior has gone with a different color palette and now use grey and silver over their blue coloring and use softer orange accents throughout, giving the stick an overall darker, sleeker look.\n" +
                "\n" +
                "Moving to the construction of the Covert QRE SL Senior, Warrior went with their True1 Phantom design. This means that the entire shaft is a true one-piece build! This is paired with their Minimus 1200 Carbon Fiber on the interior, which provides a reduction in weight, but also makes the Covert QRE SL Stick that much more durable. And, for this stick, Warrior has re-layered these carbon fiber materials, shaving off 16 grams compared to the QREdge. These two features together provide players with a stick that is lighter, more durable, and more responsive than ever before.\n" +
                "\n" +
                "Warrior went with a texturized grip for their new Covert QRE SL Senior. They paired this with their straight side walls with rounded corners. The Covert line of sticks from Warrior is designed to have one of the quickest, if not THE quickest, releases on the market. The Covert QRE SL Stick is no different. This is thanks to Warrior’s new redesigned 3D Edge Taper. This taper drives flex energy through the hosel providing lightning quick releases on your shots all while remaining deadly accurate.\n" +
                "\n" +
                "The redesigned taper of the Covert QRE SL Senior Hockey Stick goes hand in hand with the SoftCore X Foam Core constructed blade. This material is 20% lighter and more responsive than its predecessor. They also use their Twin-Spar Blade Structure which is the use of two carbon fiber support structures to reinforce the blade, increasing longevity and durability.\n" +
                "\n" +
                "The Covert QRE SL Senior features Warrior’s popular low kick-point. This stick is optimized for players who take quick wrist shots right around the hashmarks.\n" +
                "\n" +
                "Thanks to the restructured carbon fiber layout and the redesigned taper, the Covert QRE SL Senior is Warrior’s most advanced stick to date. If you are a player looking for a stick with the quickest release possible, in an extremely lightweight package, look no further than the Warrior Covert QRE SL Grip Senior Hockey Stick!");

        this.productDescription.put("Warrior Covert QRE5 Grip Senior Hockey Stick", "Covert QR Edge technology is unlike anything seen before in a hockey stick. We created the Edge Taper which maximizes the energy transfer by focusing the energy down into the blade for our fastest release yet.\n" +
                "\n" +
                "Edge Quick Release | Easier to load for a more explosive, quick release\n" +
                "Edge Taper | New angular edge taper drives flex energy and magnifies power and quick release\n" +
                "Geometry | Unique geometry improves response and is more stable and plays stronger\n" +
                "Minimus Carbon 400 | Flat weave carbon composite design improves balance and playability\n" +
                "True 1 Phantom | Reengineered True 1 piece construction enhances how the stick feels during play giving you more control and response");

        this.productDescription.put("CCM Super Tacks 9280 Grip Senior Hockey Stick", "Brand new for this season is CCM latest iteration to their mid-kick stick line, with the introduction of the CCM Super Tacks 9280 Senior Ice Hockey Stick!\n" +
                "\n" +
                "The all-in-one flex profile of the Super Tacks 9280 Hockey stick features two different stiffness zones allowing players to quickly load up both slap and wrist shots.\n" +
                "\n" +
                "Further assisting in getting the puck off with maximum power is the Ultra Attackframe blade. This blade, constructed with a C6 carbon weave material, is highly lightweight, and remains stiff during shooting and passing. As a result, this will help players have better control and accuracy of the puck.\n" +
                "\n" +
                "The reoptimized taper at the bottom third of the Super Tacks 9280 Hockey Stick has created a more balanced feel, providing greater stability and control when leaning into heavy shots like slap shots! This redesigned shape has also helped reduce the overall weight of the stick, resulting in an incredibly balanced stick with unmatched feel.\n" +
                "\n" +
                "If you are a high-level player looking for maximum power and quick loading times, without breaking the bank, check out the CCM Super Tacks 9280 Senior Ice Hockey Stick!");

        this.productDescription.put("Twigz ABS Senior Wood Hockey Stick", "Brand new for 2018, Twigz has brought out their own line of sticks to the hockey market! While taking a clever spin from the Twigz goalie stick name, Twigz used the same high quality materials to provide high quality sticks without breaking the bank.\n" +
                "\n" +
                "Starting with the look, the Twigz ABS Senior Wood Hockey Stick features a great design with a white top fading to a natural exposed finish. On the bottom, there are small Twigz accents with Moving up the shaft; you will find another Twigz logo with the ABS logo in red. This entire look is encapsulated in a gloss finish which prevents premature wear or exposed splinters throughout use.\n" +
                "\n" +
                "The Twigz ABS Senior is made from a full aspen wood shaft with an ABS blade which provides a good feel and great durability. This design provides a good weight with great durability for extended usage on hard or doutdoor surfaces.\n" +
                "\n" +
                "If you are a mid-level player who wants a durable stick with solid performance all without damaging your wallet, look no further than the Twigz ABS Senior Wood Hockey Stick.");

        /**
         * Runner
         */
        this.productDescription.put("Bladetech Speed Blade +4.0 Stainless Steel Runner - Pair", "New for 2018 are the BladeTech CCM Speed Blade 4.0 Stainless Steel Blades made by BladeTech Hockey. BladeTech Hockey focuses on two attributes: innovation and quality. With this in mind, they are the first company to introduce “flexible” hockey blades that help increase performance with added health benefits. Additionally, these blades are a direct replacement to your stock manufacturer blades.\n" +
                "\n" +
                "This patented technology is known as Flex-Force. This is a slight cutout in the blade right under the ball of your foot. Thanks to the unique blade design, the front portion of the BladeTech Stainless Steel Blades flex up into the holder of your skates, stores the vertical energy, and then releases it when you extend your stride. This innovation will increase your acceleration and help you skate up to an average of 5% faster!\n" +
                "\n" +
                "Not only do the BladeTech Stainless Steel Blades for CCM’s Speed Blade 4.0 Holders increase your speed and acceleration, but they also help reduce lower body injuries. Thanks to the flex of the blade into the holder, it absorbs the large-impact forces while skating and reduces pressure on your ankles, knees, and hip joints. Not only is this a benefit for your body, but it will also help you to be more efficient on the ice and allow you to play longer!\n" +
                "\n" +
                "With the addition of their Flex-Force Technology, BladeTech Blades look to make a name for themselves within the aftermarket steel community. If you are a player looking for increased speed and less joint pain, look no further than the BladeTech Stainless Steel Blades!");

        this.productDescription.put("Massive Blade LS Edge Classic Replacement Steel - Pair", "Starting in 2013, Massive Blade began developing cutting edge blades to meet the needs of hockey players. Now in 2018, Massive has used 5 years of industry research to bring out the best technology for player steel.\n" +
                "\n" +
                "Beginning with the manufacturing, Massive Blade utilizes Vacuum Heat Treatment for all their products to ensure the highest quality steel. This process helps reduce reactions and changes in the surface properties of the metal which provides a better and harder edge over time. Harder edges will ultimately help reduces the amount of nicks, dents, or rolled edges that might happen during game play.\n" +
                "\n" +
                "Massive Blade’s Lightspeed Edge Classic Ice Hockey Runner is traditional non-coated stainless steel made for any Edge holder. This 20-millimeter tall steel will come stock with an 11-foot radius which will allow you to get lower into turns without bottoming out.\n" +
                "\n" +
                "If you are looking for steel to give you a crisp long-lasting edge, the Massive Blade’s Lightspeed Edge Classic Ice Hockey Runner is the blade for you.");

        this.productDescription.put("Massive Blade LS2 Gold Replacement Runner - Pair", "Starting in 2013, Massive Blade began developing cutting edge blades to meet the needs of hockey players. Now in 2018, Massive has used 5 years of industry research to bring out the best technology for player steel.\n" +
                "\n" +
                "Beginning with the manufacturing, Massive Blade utilizes Vacuum Heat Treatment for all their products to ensure the highest quality steel. This process helps reduce reactions and changes in the surface properties of the metal which provides a better and harder edge over time. Harder edges will ultimately help reduces the amount of nicks, dents, or rolled edges that might happen during game play.\n" +
                "\n" +
                "Massive Blade’s Lightspeed 2 Gold Ice Hockey Runner is for players looking for a harder steel, compared to the classic. This steel is specifically designed for the LightSpeed 2 holder. Massive bonds their Titanium Nitride to a stainless-steel core which results in a blade with more corrosion resistance and longer edge life. This 20-millimeter tall steel will come stock with an 11-foot radius which will allow you to get lower into turns without bottoming out.\n" +
                "\n" +
                "If you are looking for a steel that will give you a hard, long-lasting edge; then check out the Massive Blade LightSpeed Edge Gold Replacement Runner.");

        this.productDescription.put("Massive Blade Speed Blade Gold Replacement Runner - Pair", "Starting in 2013, Massive Blade began developing cutting edge blades to meet the needs of hockey players. Now in 2018, Massive has used 5 years of industry research to bring out the best technology for player steel.\n" +
                "\n" +
                "Beginning with the manufacturing, Massive Blade utilizes Vacuum Heat Treatment for all their products to ensure the highest quality steel. This process helps reduce reactions and changes in the surface properties of the metal which provides a better and harder edge over time. Harder edges will ultimately help reduces the amount of nicks, dents, or rolled edges that might happen during game play.\n" +
                "\n" +
                "Massive Blade’s Speed Blade Gold Ice Hockey Runner is for players looking for a harder steel, compared to the classic. This steel is specifically made for the Speed Blade holder. Massive bonds their Titanium Nitride to a stainless-steel core which results in a blade with more corrosion resistance and longer edge life. This 20-millimeter tall steel will come stock with an 11-foot radius which will allow you to get lower into turns without bottoming out.\n" +
                "\n" +
                "If you are looking for a steel that will give you a hard, long-lasting edge; then check out the Massive Blade Speed Blade Gold Replacement Runner.");

        /**
         * Soaker
         */
        this.productDescription.put("Elite Pro Blade Soakers", "Keep your skates dry and protected with the Elite Hockey Pro Blade Soakers. Specially designed as a \"slip on\" over the sharp edges of your hockey skate blades, the Elite Hockey Pro Blade Soakers provide the ultimate protection from nicks and cuts to both your hockey equipment and hockey bag.\n" +
                "\n" +
                "Protects skate blades from chipping and prolongs sharpening\n" +
                "Absorbent inner terry lining absorbs excess water to help prevent blade rust\n" +
                "Water resistant nylon outer material provides additional protection to the inner material layers\n" +
                "Reinforced integrated inner and outer polypropylene web band along the bottom\n" +
                "Sold in pairs");

        this.productDescription.put("Tuffterrys Blade Covers", "Reinforced on the bottom, both inside and outside, with durable polypropylene\n" +
                "Made of top quality American terry cloth\n" +
                "Specially enhanced liner wicks away and absorbs moisture\n" +
                "Protects blades and nearby equipment");

        this.productDescription.put("Elite Notorious Pro Ultra Dry Blade Soakers", "Specially designed to easily “slip” over the sharp edges of your skate blades\n" +
                "Provides the ultimate protection from nicks and cuts to both your hockey equipment and bag\n" +
                "Features an inner terry lining that absorbs excess water from your skate blades to help prevent them from rusting\n" +
                "Reinforced integrated inner and outer polypropylene web band on the bottom prevents skate blades from chipping and prolongs sharpening");
    }

    public String getDescription(String skuCode)
    {
        return this.productDescription.get(skuCode);
    }

}
