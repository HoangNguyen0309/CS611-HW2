# 🎮 Assignment 2 — Sliding Puzzle & Dots and Boxes

## 👤 Student Information

Hoang Nguyen  hnguy@bu.edu



---

## 📁 File Information

**Main** – Entry point; registers games, gets user input, and runs the selected game.  
**Board** – Generic 2D board that stores Piece objects and defines grid layout.  
**Piece** – Abstract representation of any object placed on a Board (e.g., Tile, Edge).  
**Position** – Represents coordinates on a Board.  
**Direction** – Enum defining movement directions.  
**Game** – Abstract interface for all games.  
**GameFactory** – Interface for creating configured instances of a game.  
**GameRegistry** – Holds all available GameFactory instances and provides lookup.  
**Rules** – Defines logic to validate moves and determine game-over or win conditions.  
**Renderer** – Interface for displaying game state.  
**ConsoleIO** – Handles user input/output via the console.  
**Tile** – Represents a tile on a grid; used in both puzzle and dots games as a point element.  
**SlidingGame** – Main controller for the sliding puzzle; manages turns, moves, and completion check.  
**SlidingFactory** – Creates a complete SlidingGame with rules, renderer, and board.  
**SlidingRules** – Implements valid-move logic.  
**SlidingState** – Maintains the board configuration and current blank position.  
**SlidingRenderer** – Displays the puzzle board in the console.  
**GridBoard** – Concrete implementation of Board for the sliding puzzle grid.  
**SlidingTile** – Represents an individual numbered tile.  
**SlideAction** – Encapsulates a single move.  
**GoalStrategy** – Interface defining what “goal state” means for the puzzle.  
**StandardGoal** – Implementation of GoalStrategy.  
**SolvabilityPolicy** – Interface for checking if a shuffled board is solvable.  
**SolverUtils** – Helper functions to test solvability or generate solvable puzzles.  
**Shuffler** – Interface for randomizing the starting board.  
**RandomMoveShuffler** – Shuffles the puzzle using valid random moves to preserve solvability.  
**SessionBest** – Tracks best performance across play sessions.  
**DotsGame** – Main controller for Dots & Boxes; handles turns, edge claiming, and scoring.  
**DotsAndBoxesFactory** – Creates and configures a full DotsGame instance.  
**DotsRules** – Validates legal moves and detects when a box is completed.  
**DotsState** – Stores game data.  
**DotsRenderer** – Renders the game board in plain text.  
**DotsRendererAnsi** – Renders board using ANSI colors for player distinction.  
**EdgePos** – Defines an edge by its start point and orientation.  
**Orientation** – Enum specifying edge direction: HORIZONTAL or VERTICAL.  
**Side** – Enum for box sides: TOP, BOTTOM, LEFT, RIGHT.  
**ClaimEdge** – Represents a move when a player claims an edge.  
**BoxPiece** – Represents a completed box, storing the owning player.  
**PlayerInfo** – Holds player details.  
**SessionStats** – Tracks session-wide statistics.  
**EdgeUtils** – Utility methods to detect adjacent edges, box completion.  

---

## ⚙️ Compile & Run Instructions

```bash
# Compile
javac app/Main.java

# Run
java app.Main
```

---

## 💡 Example I/O

### Game Selection
```
Select a game:
1) Sliding Puzzle
2) Dots & Boxes
3) Quit
Choice: 2
```

---

### Dots & Boxes Menu
```
=== Dots & Boxes ===
1) Play
2) Rules
3) High Scores
4) Back
Choice: 1
Boxes rows (>=1) [3]: 2
Boxes cols (>=1) [3]: 2
Player 1 name [Player1]: alice
Player 2 name [Player2]: bob
```

---

### Gameplay Example
```
INPUT:
- Easiest:  r c side  (side of the box -> T,B,L,R or top/bottom/left/right)
- Matrix wise:   H r c   or   V r c
- Commands: avail | edges | rules | q

Box coords: rows 0..1, cols 0..1
.   .   .
         
.   .   .
         
.   .   .
```

---

### Sample Moves
```
bob (B) move [r c side | H r c | V r c | avail | edges | rules | q]: V 1 0

.---.   .
|        
.---.   .
|        
.   .   .

alice (A) move [r c side | H r c | V r c | avail | edges | rules | q]: V 0 1

.---.   .
|   |    
.---.   .
|        
.   .   .
```

---

### Checking Available Edges
```
b (B) move [r c side | H r c | V r c | avail | edges | rules | q]: avail

Available edges (showing 3 of 3):
  Box+Side     | H/V      | Note
  ------------ | -------- | ----
  0 0 T        | H 0 0    | 
  0 0 B        | H 1 0    | 
  0 0 L        | V 0 0    | 
```

---

### Winning Move
```
b (B) move [r c side | H r c | V r c | avail | edges | rules | q]: V 0 0

.---.
|   |
.---.

Score: a=0, b=1
Winner: b
```

---

✅ **End of README**
