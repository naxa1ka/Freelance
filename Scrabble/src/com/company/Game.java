package com.company;

import com.company.Grid.Grid;
import com.company.Grid.GridStatus.GridStatus;
import com.company.Grid.GridStatus.GridStatusMessageHandler;
import com.company.Grid.GridView;
import com.company.Grid.WordGridHandler;

public class Game {
    private final GridView gridView;
    private final WordGridHandler wordGridHandler;
    private final GridStatusMessageHandler gridStatusMessageHandler;

    public Game(int size, String wordsFilePath){
        Grid grid = new Grid(size);
        WordsHolder wordsHolder = new WordsHolder(wordsFilePath);

        gridView = new GridView(grid);
        wordGridHandler = new WordGridHandler(grid, wordsHolder);
        gridStatusMessageHandler = new GridStatusMessageHandler();
    }

    public boolean tryAddLetter(int x, int y, char letter, Player player){
        GridStatus status = wordGridHandler.tryAddLetter(x, y, letter, player);
        gridStatusMessageHandler.message(status, letter);

        boolean correctStatus = status == GridStatus.CORRECT || status == GridStatus.WORD_IS_COMPOSED;
        if (correctStatus){
            gridView.draw();
        }

        return correctStatus;
    }
}
