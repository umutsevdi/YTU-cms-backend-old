
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

### Club

### Chat




