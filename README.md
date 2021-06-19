
<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/umutsevdi1/YTU-cms-backend">
    <img src="https://www.yildiz.edu.tr/images/files/ytulogo.jpg" alt="Logo" width="80" height="80">
  </a>
  
  <h3 align="center">Yıldız Technical University Club Management System API</h3>
  
   <a href="https://github.com/umutsevdi1/YTU-cms-frontend">
    <p align="center">Frontend Page</p>
  </a>
  <p align="center">Updated version of YTU CMS API.</p>
  
</p>



## API Request List
### Pre-Authentication

* `GET /timeline/?f=min,max,club` : Returns an array of club activities. Has 3 filters. Min, Max and Club. Min and Max are required for a valid response, returns all elements between last $min and $max. Club is not a mandatory value, which filters response for the given club Id. 

### Authentication
* `POST /authenticate/` : Requires body with username and password. Returns the token and public ID of the user.
* `DELETE /authenticate/` : Requires header with a token. Terminates session for the owner of token. 

### User

* `GET /users/f?=` : Returns an array of users. Can be filtered.
* `GET /users/online/f?=` : Returns an array of users that are currently online. Can be filtered.
* `GET /users/public_id/f?=` : Returns the user with given public_id. Can be filtered.
* `POST /users/` : Creates a user with given properties. **administrator only**
* `PUT /users/public_id/` : Updates the profile. **profile owner & administrator **
* `DELETE /users/public_id/` : Deletes the user. **advisor & administrator**

### Club


#### Club Information
* `GET`
  * `GET /clubs/f?=` `everyone` : Returns an array of clubs. Can be filtered. 
  * `GET /clubs/object_id/f?=` `administrator & advisor & department & club_owners` : Returns the club with given object_id. Can be filtered.
  * `GET /clubs/object_id/f?=` `others` : Returns the club with given object_id. Can be filtered. Returns only accepted, rejected or completed activities/documents.
* `POST`
  * `POST /clubs/` `administrator` : Creates a club. Also creaates president, vice_president, accountant and advisor accounts. 
* `PUT`
  * `PUT /clubs/object_id/` `club_owner & advisor`: Edits the basic information : Name, description, picture
  * `PUT /clubs/object_id/` `administrator`: Edits the basic information and club owners : Name, description, picture, president, vice_president, accountant, advisor

* `DELETE`
  * `DELETE /clubs/object_id/` `administrator` : Deletes a club. 


#### Activities & Documents & Communities
* `GET`
  * `GET /clubs/object_id/activities/` `administrator & advisor & department & club_owners` : Returns an array of club activities. Can be filtered.
  * `GET /clubs/object_id/activities/` `others` : Returns an array of club activities which are accepted, rejected or completed. Can be filtered. 
  * `GET /clubs/object_id/activities/activity_name/f?=` `administrator & advisor & department & club_owners` : Returns a single activity. Can be filtered.  
  * `GET /clubs/object_id/activities/activity_name/f?=` `others` :  : Returns a single activity if it is accepted, rejected or completed. Can be filtered.  
  * `GET /clubs/object_id/documents/` `administrator & advisor & department & club_owners` : Returns an array of club documents. Can be filtered.
  * `GET /clubs/object_id/documents/` `others` : Returns an array of club documents which are accepted, rejected or completed. Can be filtered. 
  * `GET /clubs/object_id/documents/document_name/f?=` `administrator & advisor & department & club_owners` : Returns a single document. Can be filtered.  
  * `GET /clubs/object_id/documents/document_name/f?=` `others` :  : Returns a single document if it is accepted, rejected or completed. Can be filtered.  
  * `GET /clubs/object_id/communities/` `everyone` :  Returns an array of communities.  
  * `GET /clubs/object_id/communities/community_name` `everyone` :  Returns a single community.  
* `POST`
  * `POST /clubs/activities/` `club_owner` : Creates an activity.
  * `POST /clubs/document/` `club_owner` : Creates an document.
  * `POST /clubs/communities/community_name` `club_owner` : Creates a community.
* `PUT`
  * `PUT /clubs/object_id/activities/activity_name` `club_owner` if `status==draft` : Edits all informations about the activity. 
  * `PUT /clubs/object_id/activities/activity_name` `club_owner & advisor & department & administrator` : Edits the `status`.
  * `PUT /clubs/object_id/documents/document_name` `club_owner` if `status==draft` : Edits all informations about the document. 
  * `PUT /clubs/object_id/documents/document_name` `club_owner & advisor & department & administrator` : Edits the `status`.

* `DELETE`
  * `DELETE /clubs/object_id/activities/activity_name`  `club_owner` : Deletes an activity.
  * `DELETE /clubs/object_id/document/document_name` `club_owner` : Deletes an activity.

### Chat




