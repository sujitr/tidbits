# Check If It Is a Straight Line
You are given an array coordinates, coordinates[i] = [x, y], where [x, y] represents the coordinate of a point. 
Check if these points make a straight line in the XY plane.

Example 1:
> Input: coordinates = [[1,2],[2,3],[3,4],[4,5],[5,6],[6,7]]   
> Output: true

Example 2:
> Input: coordinates = [[1,1],[2,2],[3,4],[4,5],[5,6],[7,7]]   
> Output: false

### Approach

Three points (x,y) , (x1,y1) , (x2,y2), are on the 
same line if their slopes are same. i.e. 

    (y1-y)/(x1-x) = (y2-y1)/(x2-x1)

which is actually,

    (y1-y)*(x2-x1) = (y2-y1)*(x1-x)

this will just save us from division and remainder equality check mess.