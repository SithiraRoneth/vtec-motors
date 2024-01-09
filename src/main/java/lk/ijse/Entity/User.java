/* Created By Sithira Roneth
 * Date :1/6/24
 * Time :22:27
 * Project Name :vtec-motors
 * */
package lk.ijse.Entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private String user_name;
    private String email;
    private String password;
}
