package Rooms;

import Game.Adventure;
import Items.Item;

public class Garden extends Room {
    boolean canCollect_ = false;
    public Garden() {
        super("Garden", "a beautiful garden lush with fruits and veggies");
        Item fruit = new Item("fruit", "delicious juicy fruit");
        Item veggies = new Item("veggies", "nutritious leafy greens");
        items_.add(fruit);
        items_.add(veggies);
    }

    @Override
    public void playerEntered() {
        System.out.println("You are now in the garden");
        if (!canCollect_) {
            System.out.println("So many fruits and veggies to eat!");
        }
    }

    @Override
    public boolean handleCommand( String[] subcommands ) {

        if( subcommands.length <= 1 ) {
            return false;
        }
        String cmd  = subcommands[0];
        String attr = subcommands[1];

        // unlock, use
        if( cmd.equals( "collect" ) && (attr.equals( "fruit") || attr.equals( "veggies")) ) {

            boolean hasBasket = false;
            for( Item item : Adventure.inventory ) {
                if( item.getName().equals( "Basket" ) ) {
                    hasBasket = true;
                    break;
                }
            }
            if( hasBasket ) {
                System.out.println( "You can now collect fruit or vegetables");
                canCollect_ = true;
                if (cmd.equals( "collect" ) && attr.equals( "fruit")) {
                    System.out.println("You gathered some fruit!");
                    Adventure.inventory.add(new Item("fruit", "delicious"));

                } else if (cmd.equals( "collect" ) && attr.equals( "veggies")) {
                    System.out.println("You gathered some veggies!");
                    Adventure.inventory.add(new Item("veggies", "nutritious"));
                }
            }
            else {
                System.out.println( "You don't have the basket." );
            }
            return true;
        }
        return false;
    }
}
