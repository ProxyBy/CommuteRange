# Commute Range System

## 1.	INTRODUCTION
### 1.1.	Purpose
The purpose of this document is to provide a description of the design, to define the specific requirements for the Commute Range System and to detail the specifications for the features, capabilities and major characteristics of the proposed system. Within the Software Design Document are narrative and graphical documentation of the software design for the project including use cases, architecture design, database design and application program interface.

### 1.2.	Project scope
The Commute Range System is designed to solve logistics problems. It can be used as server side system for different applications, which has commute range problems.

## 2.	GENERAL OVERWIEW
Commute Range System allows determining the reachable towns/cities from a starting point in a given amount of time. The system use Dijkstra Algorithm for finding shortest distance between cities.

### 2.1.	Base algorithm
Dijkstra's algorithm is an algorithm for finding the shortest paths between nodes in a graph. 
Let the node at which we are starting be called the initial node. Let the distance of node Y be the distance from the initial node to Y. Dijkstra's algorithm will assign some initial distance values and will try to improve them step by step.
1.	Mark all nodes unvisited. Create a set of all the unvisited nodes called the unvisited set.
2.	Assign to every node a tentative distance value: set it to zero for our initial node and to infinity for all other nodes. Set the initial node as current.
3.	For the current node, consider all of its unvisited neighbors and calculate their tentative distances through the current node. Compare the newly calculated tentative distance to the current assigned value and assign the smaller one. For example, if the current node A is marked with a distance of 6, and the edge connecting it with a neighbor B has length 2, then the distance to B through A will be 6 + 2 = 8. If B was previously marked with a distance greater than 8 then change it to 8. Otherwise, keep the current value.
4.	When we are done considering all of the unvisited neighbors of the current node, mark the current node as visited and remove it from the unvisited set. A visited node will never be checked again.
5.	If the destination node has been marked visited (when planning a route between two specific nodes) or if the smallest tentative distance among the nodes in the unvisited set is infinity (when planning a complete traversal; occurs when there is no connection between the initial node and remaining unvisited nodes), then stop. The algorithm has finished.
6.	Otherwise, select the unvisited node that is marked with the smallest tentative distance, set it as the new "current node", and go back to step 3.

Asymptotic upper bound of the algorithm is O(m log(n)) where n –number of nodes and m – number of edges


### 2.2.	Assumptions
1)	Commute Range System does not use real time data. Data is presented as a static immutable dataset.

2)	Client side system receives list of cities, which are existed in Commute Range System with the same Index. So client and server side have the same city information (Id, name).

3)	Number of nodes depends on size of JVM memory. Full graph must be loaded in memory.  

## 3.	ARCHITECTURE DESIGN
### 3.1.	Software architecture
Commute range system has MVC architecture 

## 4.	SYSTEM DESIGN
### 4.1.	Use-cases
| Use case name | Description |
| ------ | ------ |
| determine the reachable towns/cities | Commute Range System determine the reachable towns/cities from a starting point in a given amount of time. User pick the start point city and a commute time in minutes and Commute Range System should list the cities/towns, which are reachable from the start point within commute time.  |

### 4.2.	Database design
#### 4.2.1.	City table
| Column name | Type | Parameters | Description |
| ------ | ------ | ------ | ------ |
| id|	INT(11)|PK, Not Null|	Unique index of city|
name|	VARCHAR(255)|	|	City name|

#### 4.2.2.	Route table
| Column name | Type | Parameters | Description |
| ------ | ------ | ------ | ------ |
|id|	INT(11)|	PK, Not Null|	Unique index of route|
start_point|	INT(11)|	Not Null|	Index of city, which is start point of route|
end_point|	INT(11)|	Not Null	|Index of city, which is end point of route|
distance|	Double|	Not Null|	Commute time in minutes|

#### 4.2.3.	Database configuration

|Database|	MySQL 8.0.12|
| ------ | ------ |

### 4.3.	Application program interface

|End point	|Input data format	|Output data format|
| ------ | ------ | ------ |
|localhost:8080/city	|JSON 	|JSON|

### 4.4. Core interface
|Name| Description |	
| ------ | ------ | 
CommuteRangeAlgorithm | Base structure for algorithm implementation. It can be Graph, Tree, etc|
Entity| Main object that is used in the system as point. It can be city, people, country etc.|

Implementation of CommuteRangeAlgorithm must implement getReachableEntities(int startItemId, double rangeLimit), where "startItemId" - start point of searching and "rangeLimit" - max value of distance. 

### 4.5.	Data format
#### 4.5.1.	Input data format
|Field name|	Field type	| Description |	
| ------ | ------ | ------ | 
|startItemId|	Integer|	Index of city, which is start point of route |	
rangeLimit|	Double|	Commute time in minutes	|
> { 
>  "startItemId": 4,
>  "rangeLimit": 60
> }

#### 4.5.2.	Output data format
|Field name|	Field type|	Description|
| ------ | ------ | ------ | 
Id|	Integer|	Index of the reachable city|	
name|	String|	Name of the reachable city|
>[
>    {
>        "id": 0,
>        "name": "Boston"
>    },
>    {
>        "id": 2,
>        "name": "NY"
>    }
>]

 

	
