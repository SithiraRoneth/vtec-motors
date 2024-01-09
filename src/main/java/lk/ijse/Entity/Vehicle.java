/* Created By Sithira Roneth
 * Date :12/23/23
 * Time :00:39
 * Project Name :vtec-motors
 * */
package lk.ijse.Entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Vehicle {
    private String vehicle_id;
    private String vehicle_type;
    private String guardian_id;
}
