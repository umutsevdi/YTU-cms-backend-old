
<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/umutsevdi/YTU-cms-backend">
    <img src="http://www.mezun.yildiz.edu.tr/asset/img/logo_zeminsiz-01.png" alt="Logo" width="80" height="80">
  </a>
  
  <h3 align="center">Yıldız Technical University Club Management System API</h3>
  <h4 align="center">(Depreciated)</h4>
  
  
   <a href="https://github.com/umutsevdi/YTU-cms-frontend">
    <p align="center">Frontend Page</p>
  </a>
  <p align="center">Updated version of YTU CMS API.</p>
  
</p>



# API Request List
## Pre-Authentication

* `GET /timeline/?min&max&club` : Returns an array of club activities. Has 3 filters. Min, Max and Club. Min and Max are required for a valid response, returns all elements between last $min and $max. Club is not a mandatory value, which filters response for the given club Id. 

## Authentication
* `POST /authenticate/` : Requires body with username and password. Returns the token and public ID of the user.
* `DELETE /authenticate/` : Requires header with a token. Terminates session for the owner of token. 

## User
 Database properties: 
|Property|Type|
|--|--|
| `_id` |ObjectId|
| `fullname`|String|
|`mail`|String|
| `password`|String: `Hashed`|
|`role`|Enum : `UserType`|
| `club`|ObjectId|
|`email_confirmed`|Boolean|
|`is_activated`|Boolean|

Enum UserType : [`  UNASSIGNED,PRESIDENT,VICE_PRESIDENT,ACCOUNTANT,ADVISOR,DEPARTMENT,ADMIN` ]
### Requests :
  
|Command|URL|Avaliable To|Description|
|--|--|--|--|
|GET|/users/f?=|any|Returns an array of users. Can be filtered.|
|GET|/users/{_id}/f?=|any|Returns the user with given `_id`. Can be filtered|
|GET|/users/{_id}/image/|any|Returns the profile picture|
|POST|/users/|admin|Creates a user with given properties.|
|POST|/users/{_id}/image/|self|Updates the profile picture|
|PUT|/users/{_id}|self| Updates fullname or password.|
|DELETE|/users/{_id}|admin| Deletes the user|

## Club
 Database properties: 
|Property|Type|
|--|--|
| `_id` |ObjectId|
| `name`|String|
|`description`|String|
| `president`|ObjectId|
|`vice_president`|ObjectId|
| `accountant`|ObjectId|
|`advisors`|List: `ObjectId`|
|`communities`|List: `String`|
|`past_administrations`|Map : `int`, ( `Map: String, ObjectId` )|

### Requests :
  
|Command|URL|Avaliable To|Description|
|--|--|--|--|
|GET|/clubs/f?=|any|Returns an array of clubs. Can be filtered.|
|GET|/clubs/{_id}/f?=|any|Returns the club with given `_id`. Can be filtered|
|GET|/clubs/{_id}/image/|any|Returns the profile picture|
|POST|/clubs/|admin|Creates a club with given properties.|
|POST|/clubs/{_id}/image/|club_admin, admin|Updates the profile picture|
|PUT|/clubs/{_id}|club_admin| Updates the club name, description or communities.|
|PUT|/clubs/{_id}/assign/|admin|Assings a `president`, a `vice_president`, an `accountant` or `advisors` to the club. Requires mail addresses. If mail address doesn't exist in the database, creates an empty user and sends a mail to their addresses which contains their password. Old president, vice president and accountants are set as `UNASSIGNED`.|
|DELETE|/clubs/{_id}|admin| Deletes the club|

## Events
 Database properties: 
|Property|Type|
|--|--|
| `_id` |ObjectId|
| `name`|String|
|`description`|String|
| `club`|ObjectId|
|`start_date`|Date|
| `start_date`|Date|
|`status`|Enum: `StatusType`|
|`departments`|Map : `ObjectId,Boolean`|
|`representer`|String|
|`supporter_clubs`|List: `ObjectId`|
|`location`|String|
|`speakers`|List: `String`|
|`companies`|List:  `String`|

Enum StatusType : [`  DRAFT,REQUESTED,AWAITING,ACCEPTED,REJECTED,COMPLETED` ]
### Requests :
  
|Command|URL|Avaliable To|Description|
|--|--|--|--|
|GET|/events/f?=|any|Returns an array of events. Can be filtered.|
|GET|/events/{club_id}/f?=|any|Returns an array of events that belong to given `club_id`. Can be filtered|
|GET|/events/{club_id}/{_id}/f?=|any|Returns a single event that belong to given `club_id`. Can be filtered|
|GET|/events/{_id}/image/|any|Returns the event picture|
|POST|/events/|club_admin|Creates an event with given properties.|
|POST|/events/{_id}/image/|club_admin|Updates the picture of event|
|PUT|/events/{_id}|club_admin| Updates name or description of an event.|
|PUT|/events/{_id}/status|club_admin, advisor, department, admin| Changes the status of the event.|
|DELETE|/events/{_id}|admin| Deletes the event|

## Documents
 Database properties: 
|Property|Type|
|--|--|
| `_id` |ObjectId|
| `name`|String|
|`description`|String|
| `club`|ObjectId|
|`date`|Date|
|`status`|Enum: `StatusType`|
|`file`|File|

Enum StatusType : [`  DRAFT,REQUESTED,AWAITING,ACCEPTED,REJECTED,COMPLETED` ]
### Requests :
  
|Command|URL|Avaliable To|Description|
|--|--|--|--|
|GET|/documents/f?=|any|Returns an array of documents. Can be filtered.|
|GET|/documents/{club_id}/f?=|any|Returns an array of documents that belong to given `club_id`. Can be filtered|
|GET|/documents/{club_id}/{_id}/f?=|any|Returns a single document that belong to given `club_id`. Can be filtered|
|GET|/documents/{_id}/file/|any|Downloads the file of the document.|
|POST|/documents/|club_admin|Creates an document with given properties.|
|POST|/documents/{_id}/file/|club_admin|Uploads a file for the document.|
|PUT|/documents/{_id}|club_admin| Updates name or description of an document.|
|PUT|/documents/{_id}/status|club_admin, advisor, department, admin| Changes the status of the document.|
|DELETE|/documents/{_id}|admin| Deletes the document|

### Chat
* Java Web Flux will be implemented
* `POST /chat/public_id` `everyone` : Sends a message to another user. Can send a file.



