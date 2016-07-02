package com.usbus.dal.dao;

import com.usbus.commons.auxiliaryClasses.Image;
import com.usbus.commons.auxiliaryClasses.TenantStyle;
import com.usbus.commons.auxiliaryClasses.TenantStyleAux;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.Tenant;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

/**
 * Created by jpmartinez on 08/05/16.
 */
public class TenantDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public TenantDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public String persist(Tenant tenant) {
        return dao.persist(tenant);
    }

    public long countAll() {
        return dao.count(Tenant.class);
    }

    public Tenant getById(String id) {
        return dao.get(Tenant.class, id);
    }

    public void remove(String id){
        dao.remove(Tenant.class, id);
    }

    public Tenant getByLocalId(Long id){

        if (!(id>0)) {
            return null;
        }

        Query<Tenant> query = ds.createQuery(Tenant.class);

        query.limit(1).criteria("tenantId").equal(id);

        return query.get();

    }

    public Tenant getByName(String name){

        if (name == null || name.isEmpty()) {
            return null;
        }

        Query<Tenant> query = ds.createQuery(Tenant.class);

        query.limit(1).criteria("name").equal(name);

        return query.get();

    }

    public String saveTenantStyle(long tenantId, String logo, String logoExtension,
                                    String header, String headerExtension, String busColor,
                                    Boolean showBus, String theme) throws IOException {
        if (!((tenantId <= 0))) {
            Tenant tenantOriginal = getByLocalId(tenantId);
            if (tenantOriginal != null) {
                String OS = System.getProperty("os.name");
                String stringAux;
                TenantStyle styleFromTenant = tenantOriginal.getStyle();
                if (styleFromTenant == null) {
                    styleFromTenant = new TenantStyle();
                }
                ////////////////////////////////////////////////////////////////////////////////////////
                ////////////////                                                        ////////////////
                ////////////////                          LOGO                          ////////////////
                ////////////////                                                        ////////////////
                ////////////////////////////////////////////////////////////////////////////////////////

                String logoName = "logo";
                if (!(logo == null || (logo != null && logo.isEmpty())) && !(logoExtension == null || (logoExtension != null && logoExtension.isEmpty()))) {
                    File path = null;
                    if (OS.startsWith("Windows")) {
                        stringAux = "C:" + File.separator + "USBus" + File.separator + "Images" + File.separator +
                                tenantOriginal.getName() + File.separator + logoName + "." + logoExtension;
                        path = new File("C:" + File.separator + "USBus" + File.separator + "Images" + File.separator
                                + tenantOriginal.getName() + File.separator);
                        if (!(path.exists() && path.isDirectory())) {
                            path.mkdirs();
                        }
                    } else {
                        stringAux = File.separator + "USBus" + File.separator + "Images" + File.separator +
                                tenantOriginal.getName() + File.separator + logoName + "." + logoExtension;
                        path = new File(File.separator + "USBus" + File.separator + "Images" + File.separator +
                                tenantOriginal.getName() + File.separator);
                        if (!(path.exists() && path.isDirectory())) {
                            path.mkdirs();
                        }
                    }
                    byte[] decoded = Base64.getDecoder().decode(logo);
                    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(decoded));
                    File imageFile = new File(stringAux);
                    ImageIO.write(bufferedImage, logoExtension, imageFile);

                    //Agregar el logo al tenant
                    Image image = new Image(path.toString(), logoName, logoExtension);
                    styleFromTenant.setLogo(image);
                }

                ////////////////////////////////////////////////////////////////////////////////////////
                ////////////////                                                        ////////////////
                ////////////////                         HEADER                         ////////////////
                ////////////////                                                        ////////////////
                ////////////////////////////////////////////////////////////////////////////////////////

                String headerName = "header";
//                if (!(header == null || (header != null && header.isEmpty())) && !(headerExtension == null || (headerExtension != null && headerExtension.isEmpty()))) {
//                if((!(header == null) || (header != null && !header.isEmpty())) && (!(headerExtension == null) || (headerExtension != null && !header.isEmpty()))) {
//                }
                if (!(header==null || header.isEmpty()) && !(headerExtension==null || headerExtension.isEmpty())){
                    File pathHeader = null;
                    if (OS.startsWith("Windows")) {
                        stringAux = "C:" + File.separator + "USBus" + File.separator + "Images" + File.separator +
                                tenantOriginal.getName() + File.separator + headerName + "." + headerExtension;
                        pathHeader = new File("C:" + File.separator + "USBus" + File.separator + "Images" +
                                File.separator + tenantOriginal.getName() + File.separator);
                        if (!(pathHeader.exists() && pathHeader.isDirectory())) {
                            pathHeader.mkdirs();
                        }
                    } else {
                        stringAux = File.separator + "USBus" + File.separator + "Images" + File.separator +
                                tenantOriginal.getName() + File.separator + headerName + "." + headerExtension;
                        pathHeader = new File(File.separator + "USBus" + File.separator + "Images" +
                                File.separator + tenantOriginal.getName() + File.separator);
                        if (!(pathHeader.exists() && pathHeader.isDirectory())) {
                            pathHeader.mkdirs();
                        }
                    }
                    byte[] decodedHeader = Base64.getDecoder().decode(header);
                    BufferedImage bufferedImageHeader = ImageIO.read(new ByteArrayInputStream(decodedHeader));
                    File imageFileHeader = new File(stringAux);
                    ImageIO.write(bufferedImageHeader, headerExtension, imageFileHeader);

                    //Agregar el header al tenant
                    System.out.println(pathHeader + headerName + headerExtension);
                    Image imageHeader = new Image(pathHeader.toString(), headerName, headerExtension);
                    System.out.println(imageHeader);
                    styleFromTenant.setHeaderImage(imageHeader);
                }

                ////////////////////////////////////////////////////////////////////////////////////////
                ////////////////                                                        ////////////////
                ////////////////                          BUS                           ////////////////
                ////////////////                                                        ////////////////
                ////////////////////////////////////////////////////////////////////////////////////////

                if (!(busColor == null || (busColor != null && busColor.isEmpty()))) {
                    styleFromTenant.setBusColor(busColor);
                }

                ////////////////////////////////////////////////////////////////////////////////////////
                ////////////////                                                        ////////////////
                ////////////////                        SHOWBUS                         ////////////////
                ////////////////                                                        ////////////////
                ////////////////////////////////////////////////////////////////////////////////////////

                if(showBus != null){
                    styleFromTenant.setShowBus(showBus);
                }

                ////////////////////////////////////////////////////////////////////////////////////////
                ////////////////                                                        ////////////////
                ////////////////                         THEME                          ////////////////
                ////////////////                                                        ////////////////
                ////////////////////////////////////////////////////////////////////////////////////////

                if (!(theme == null || (theme != null && theme.isEmpty()))) {
                    styleFromTenant.setTheme(theme);
                }

                tenantOriginal.setStyle(styleFromTenant);

                return dao.persist(tenantOriginal);
            }
        }
        return null;
    }

    public TenantStyleAux getTenantStyle(String tenantName) throws IOException {
        if(tenantName != null && !tenantName.isEmpty()) {
            Tenant tenant = getByName(tenantName);
            TenantStyle tenantStyle = tenant.getStyle();

            if(tenantStyle == null){
                tenantStyle = new TenantStyle();
            }

            Image logo = tenantStyle.getLogo();
            Image header = tenantStyle.getHeaderImage();

            TenantStyleAux tenantStyleAux = new TenantStyleAux();

            File logoFileAux = new File(logo.getFilePath()+File.separator+logo.getName()+"."+logo.getExtension());
            if(logoFileAux.exists()){
                BufferedImage logoBufferedImage = ImageIO.read(logoFileAux);
                String logoB64 = encodeToString(logoBufferedImage, logo.getExtension());
                tenantStyleAux.setLogoB64(logoB64);
                tenantStyleAux.setLogoExtension(logo.getExtension());
                //System.out.println(tenantStyleAux.getLogoB64());
            } else {
                tenantStyleAux.setLogoB64(null);
                tenantStyleAux.setLogoExtension(null);
            }

            File headerFileAux = new File(header.getFilePath()+File.separator+header.getName()+"."+header.getExtension());
            if(headerFileAux.exists()) {
                BufferedImage headerBufferedImage = ImageIO.read(headerFileAux);
                String headerB64 = encodeToString(headerBufferedImage, header.getExtension());
                tenantStyleAux.setHeaderB64(headerB64);
                tenantStyleAux.setHeaderExtension(header.getExtension());
                //System.out.println(tenantStyleAux.getHeaderB64());
            } else {
                tenantStyleAux.setHeaderB64(null);
                tenantStyleAux.setHeaderExtension(null);
            }

            tenantStyleAux.setTheme(tenantStyle.getTheme());

            tenantStyleAux.setBusColor(tenantStyle.getBusColor());

            tenantStyleAux.setShowBus(tenantStyle.getShowBus());

            return tenantStyleAux;
        }
        return null;
    }

    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public void clean(){
        ds.delete(ds.createQuery(Tenant.class));
    }

    public long getNextId(){
        return dao.count(Tenant.class) + 1;
    }
}
