/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.game;

import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author ArwaKhaled
 */
public class GameStateImp implements GameState<XOGameMove> {

    private char[] board; 
    
    private Player currentPlayer; 
    private XOGameMove lastMove;
    private Vector<Integer> validBoard ;
    //int index ;
    public GameStateImp() {
        validBoard= new Vector<> ();
        for(int i=0;i<9;i++){
        validBoard.add(i);}
        board = new char[9]; 
        currentPlayer = Player.one;
        lastMove = null;
    }
    public GameStateImp( GameStateImp oldGame , XOGameMove lastMove ) {
        
        validBoard= (Vector<Integer>)oldGame.validBoard.clone();
        board = oldGame.board.clone(); 
        currentPlayer = oldGame.currentPlayer==Player.one? Player.two : Player.one;
        this.lastMove =lastMove  ;
         validBoard.remove(lastMove.getIndex());
         board[lastMove.getIndex()]= oldGame.currentPlayer==Player.one?'X':'O';
    }

    @Override
    public Player getNextTurnPlayer() {
      return currentPlayer ;
    }

    @Override
    public boolean isValidMove(XOGameMove move) {
       return  move.getPlayer()==currentPlayer&&board[move.getIndex()]=='\0' ;   
    }
    

    @Override
    public Iterator getValidMoves() {
        Iterator<Integer>it=validBoard.iterator();
         return new Iterator<XOGameMove>(){
             @Override
             public boolean hasNext() {
                return it.hasNext();  
             }

             @Override
             
             public XOGameMove next() {
               int index =  it.next();
                return new XOGameMove(index);
             } 
         } ;
        
        
    }

    @Override
    public GameState play(XOGameMove move) throws IllegalStateException {
        if(!isValidMove(move)){
        throw  new IllegalStateException ("invalid move ");
        }
         return new GameStateImp(this,move);
        
    }
//board[0] and board[4] and board[8]== X 
// board[2]and board [4] and board[6] == X bardo 
// 0  1  2
// 3  4  5
// 6  7  8 
    
//board[i] and board[i+3] aand board [i+6] == 
// board [i] and board[i+1] and board [i+2] ==
    
    @Override
    public boolean isEndState() {
         if(validBoard.isEmpty())
             return true;
         if (isXWinner())
             return true; 
         else if (isOWinner())
            return true ;
        return false ;
        
    }
    
   boolean  isXWinner(){
     if(board[0]=='X'&&board[4]=='X' &&board[8]=='X')
         return true;
     else if(board[2]=='X'&&board[4]=='X' &&board[6]=='X')
         return true;
       for (int i=0;i<=2;i++){
      if( board[i]=='X' &&board[i+3]=='X' && board [i+6] == 'X')
          return true ;
       }
       
       for(int i=0;i<9;i+=3){
      if( board[i]=='X' &&board[i+1]=='X' && board [i+2] == 'X')
          return true ;
       }
       return false  ;
     
    }
   boolean  isOWinner(){
     if(board[0]=='O'&&board[4]=='O' &&board[8]=='O')
         return true;
     else if(board[2]=='O'&&board[4]=='O' &&board[6]=='O')
         return true;
       for (int i=0;i<=2;i++){
      if( board[i]=='O' &&board[i+3]=='O' && board [i+6] == 'O')
          return true ;
       }
       
       for(int i=0;i<9;i+=3){
      if( board[i]=='O' &&board[i+1]=='O' && board [i+2] == 'O')
          return true ;
       }
       return false  ;
     
    }

    @Override
    public Player getWinner() throws IllegalStateException {
        if(isEndState()){
         if (isXWinner())
             return Player.one; 
        else if (isOWinner())
            return Player.two ;
         return null ;
        } else throw new IllegalStateException("its not an end state");
        
       
         
       
    }
    @Override
    public XOGameMove getLastMove() {
        return lastMove;
    }
    
}
