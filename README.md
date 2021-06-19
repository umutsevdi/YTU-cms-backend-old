
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

`GET /timeline/?f=min,max,club` : Returns an array of club activities. Has 3 filters. Min, Max and Club. Min and Max are required for a valid response, returns all elements between last $min and $max. Club is not a mandatory value, which filters response for the given club Id. 

### Authentication
`POST /authenticate/` : Requires body with username and password. Returns the token and public ID of the user.
`DELETE /authenticate/` : Requires header with a token. Terminates session for the owner of token. 

### User

`GET /users/f?=` : Returns an array of users. Can be filtered.
`GET /users/online/f?=` : Returns an array of users that are currently online. Can be filtered.
`GET /users/public_id/f?=` : Returns the user with given public_id. Can be filtered.
`POST /users/` : Creates a user with given properties. **administrator only**
`PUT /users/public_id/` : Updates the profile. **profile owner & administrator **
`DELETE /users/public_id/` : Deletes the user. **advisor & administrator**

### Club

`GET /clubs/f?=` : Returns an array of clubs. Can be filtered. 
`GET /clubs/object_id/f?=` : Returns the club with given object_id. Can be filtered. **administrator & advisor & department & club_owners**: `view_all`. **non_club_owners** `view_only accepted & rejected & completed`
`GET /clubs/object_id/activities/` Returns an array of club activities. Can be filtered. **administrator & advisor & department & club_owners**: `view_all`. **non_club_owners** `view_only accepted & rejected & completed`
`GET /clubs/object_id/activities/activity_name/f?=` : Returns a single activity. Can be filtered.  **administrator & advisor & department & club_owners**: `view_all`. **non_club_owners** `view_only accepted & rejected & completed`
`GET /clubs/object_id/documents/` Returns an array of club documents. Can be filtered. **administrator & advisor & department & club_owners**: `view_all`. **non_club_owners** `view_only accepted & rejected & completed`
`GET /clubs/object_id/documents/document_name/f?=` : Returns a single document. Can be filtered.  **administrator & advisor & department & club_owners**: `view_all`. **non_club_owners** `view_only accepted & rejected & completed`
`POST /clubs/` : Creates a club. Also creaates president, vice_president, accountant and advisor accounts. **administrator** 
`POST /clubs/activities/` : Creates an activity. **club owner**
`POST /clubs/document/` : Creates an document. **club owner**
`PUT /clubs/object_id/`: **club owner** `edit basic_info`. **administrator** `edit_all`
`PUT /clubs/object_id/activities/activity_name` : Edits an activity. **club owner** : `edit_all` if `status==draft` else `edit_status` **advisor & department & administrator `edit_status`**
`PUT /clubs/object_id/document/document_name` : Edits an document.  **club owner** : `edit_all` if `status==draft` else `edit_status` **advisor & department & administrator `edit_status`**
`DELETE /clubs/object_id/` : Deletes a club. **administrator** 
`DELETE /clubs/object_id/activities/activity_name` : Deletes an activity. **club owner**
`DELETE /clubs/object_id/document/document_name` : Deletes an document. **club owner**
### Chat




