<h1 align="center">Project managment</h1>

<h3 align="center">This is a simple project that manages projects. </h3>

<h4>Core Technologies:</h4> 
<p>
    WebFlux, R2DBC, PostgreSQL, gradle, SpringSecurity, docker-compose, keycloak
</p>

<h3>How to run </h3>

<ol>
  <li>Run compose: docker-compose -f compose.yml up</li>
  <li>Configure Keycloak:
    <ol>
      <li>Go in browser and open http://localhost:28080</li>
      <li>login with username 'admin' and password 'admin'</li>
      <li>Click export and set master-realm-export.json</li>
      <li>On 'Master' click 'add realm' than click 'import select file' and choose 'realm-export.json'</li>
      <li>Click users and add user, set role ADMIN to user</li>
    </ol>
  </li>
<li>Optional - run UI from "https://github.com/fedorovIgor/task_manager_UI"</li>
</ol>


