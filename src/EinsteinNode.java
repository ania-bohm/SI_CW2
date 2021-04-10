public class EinsteinNode extends Node{
    EinsteinNode leftNeighbour, rightNeighbour;
    int houseNumber, colour, nationality, drink, tabaco, pet;
    EinsteinNode(int houseNumber){
        leftNeighbour = null;
        rightNeighbour = null;
        this.houseNumber = houseNumber;
        colour = 0;
        nationality = 0;
        drink = 0;
        tabaco = 0;
        pet = 0;
    }
}
