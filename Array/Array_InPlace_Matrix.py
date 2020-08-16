# Leetcode 289. Game of Life
# record previous state of each number
# 0 -> 1 assign 2
# 1 -> 0 assign 3
# when add to count, use mod 2 to track previous state
# after update, go through entire board, if 2 change to 1, if 3 change to 0
def gameOfLife(self, board: List[List[int]]) -> None:
    """
    Do not return anything, modify board in-place instead.
    """
    n = len(board[0])
    m = len(board)
    for i in range(m):
        for j in range(n):
            gameOfLifehelper(board, i, j)
    
    for i in range(m):
        for j in range(n):
            if (board[i][j]==2):
                board[i][j] = 1
            if (board[i][j]==3):
                board[i][j] = 0

def gameOfLifehelper(self, board, m, n):
    count = 0
    for i in range(m-1, m+2):
        for j in range(n-1, n+2):
            if (i==m and j==n) or (i<0) or (j<0) or (i>len(board)-1) or (j>len(board[0])-1):
                continue
            else:
                count += board[i][j] % 2

    if (count==3):
        if (board[m][n]==0):
            board[m][n]=2
    if (count<2 or count>3):
        if (board[m][n]==1):
            board[m][n]=3
                
