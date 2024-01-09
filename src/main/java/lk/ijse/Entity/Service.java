/* Created By Sithira Roneth
 * Date :1/2/24
 * Time :23:59
 * Project Name :vtec-motors
 * */
package lk.ijse.Entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Service {
    private String id;
    private String name;
    private String description;
    private double amount;
}
