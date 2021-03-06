package com.meijia.utils.huanxin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meijia.utils.huanxin.comm.Constants;
import com.meijia.utils.huanxin.comm.HTTPMethod;
import com.meijia.utils.huanxin.comm.Roles;
import com.meijia.utils.huanxin.utils.JerseyUtils;
import com.meijia.utils.huanxin.vo.ClientSecretCredential;
import com.meijia.utils.huanxin.vo.Credential;
import com.meijia.utils.huanxin.vo.EndPoints;

/**
 * REST API Demo : 用户体系集成 Jersey2.9实现
 * 
 * Doc URL: http://www.easemob.com/docs/rest/userapi/
 * 
 * @author Lynch 2014-09-09
 * 
 */
public class EasemobIMUsers {

	private static Logger LOGGER = LoggerFactory.getLogger(EasemobIMUsers.class);
	private static final String APPKEY = Constants.APPKEY;
	private static JsonNodeFactory factory = new JsonNodeFactory(false);

    // 通过app的client_id和client_secret来获取app管理员token
    private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID,
            Constants.APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);

    public static void main(String[] args) {
    		
//	    	ObjectMapper mapper = new ObjectMapper();
//	    	JsonFactory factory = mapper.getJsonFactory();
//	    	JsonParser jp;
//			try {
//				jp = factory.createJsonParser( "{\"action\":\"post\",\"application\":\"fc294ae0-ade8-11e4-9922-3f1fe83e6c2c\",\"path\":\"/users\",\"uri\":\"https://a1.easemob.com/yougeguanjia/yougeguanjia/users\",\"entities\":[{\"uuid\":\"c3eb3b9a-d3a7-11e4-8621-43b15b304627\",\"type\":\"user\",\"created\":1427367822793,\"modified\":1427367822793,\"username\":\"18512514665\",\"activated\":true}],\"timestamp\":1427367822790,\"duration\":29,\"organization\":\"yougeguanjia\",\"applicationName\":\"yougeguanjia\",\"statusCode\":200}"  );
//				JsonNode json = mapper.readTree(jp);
//				ObjectNode createNewIMUserSingleNode = (ObjectNode)(json);
//				JsonNode statusCode = createNewIMUserSingleNode.get("statusCode");
//				JsonNode entity = createNewIMUserSingleNode.get("entities");
//				System.out.println(entity.get(0).get("uuid"));
//			} catch (JsonParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} //remember to escape "
	    
    		
	    
	    	
    		
        /**
         * 注册IM用户[单个]
         */
//        ObjectNode datanode = JsonNodeFactory.instance.objectNode();
//        datanode.put("username","18512514665");
//        datanode.put("password", "12345678");
//        ObjectNode createNewIMUserSingleNode = createNewIMUserSingle(datanode);
//        if (null != createNewIMUserSingleNode) {
//            LOGGER.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
//        }

        /**
         * IM用户登录
         */
//        ObjectNode imUserLoginNode = imUserLogin(datanode.get("username").asText(), datanode.get("password").asText());
//        if (null != imUserLoginNode) {
//            LOGGER.info("IM用户登录: " + imUserLoginNode.toString());
//        }

        /**
         * 注册IM用户[批量生成用户然后注册]
         */
//        String usernamePrefix = "kenshinnuser";
//        Long perNumber = 10l;
//        Long totalNumber = 100l;
//        ObjectNode createNewIMUserBatchGenNode = createNewIMUserBatchGen(usernamePrefix, perNumber, totalNumber);
//        if (null != createNewIMUserBatchGenNode) {
//            LOGGER.info("注册IM用户[批量]: " + createNewIMUserBatchGenNode.toString());
//        }

        /**
         * 获取IM用户[主键查询]
         */
        String userPrimaryKey = "simi-user-1";
        ObjectNode getIMUsersByPrimaryKeyNode = getIMUsersByPrimaryKey(userPrimaryKey);
        if (null != getIMUsersByPrimaryKeyNode) {
            LOGGER.info("获取IM用户[主键查询]: " + getIMUsersByPrimaryKeyNode.toString());
        }

        /**
         * 重置IM用户密码 提供管理员token
         */
//		String username = "18612514665";
//        ObjectNode json2 = JsonNodeFactory.instance.objectNode();
//        json2.put("newpassword", Constants.DEFAULT_PASSWORD);
//        ObjectNode modifyIMUserPasswordWithAdminTokenNode = modifyIMUserPasswordWithAdminToken(username, json2);
//        if (null != modifyIMUserPasswordWithAdminTokenNode) {
//            LOGGER.info("重置IM用户密码 提供管理员token: " + modifyIMUserPasswordWithAdminTokenNode.toString());
//        }
//        ObjectNode imUserLoginNode2 = imUserLogin(username, json2.get("newpassword").asText());
//        if (null != imUserLoginNode2) {
//            LOGGER.info("重置IM用户密码后,IM用户登录: " + imUserLoginNode2.toString());
//        }
			
		/**
		 * 修改IM用户昵称
		 */			
//		String username = "simi-sec-1";
//		ObjectNode json2 = JsonNodeFactory.instance.objectNode();
//		json2.put("nickname", "小茶3");
//		ObjectNode modifyIMUserNickName = modifyIMUserNickName(username, json2);
//		if (null != modifyIMUserNickName) {
//		    LOGGER.info("修改IM昵称: " + modifyIMUserNickName.toString());
//		}
	

        /**
         * 添加好友[单个]
         */
//        String ownerUserPrimaryKey = username;
//        String friendUserPrimaryKey = "kenshinnuser099";
//        ObjectNode addFriendSingleNode = addFriendSingle(ownerUserPrimaryKey, friendUserPrimaryKey);
//        if (null != addFriendSingleNode) {
//            LOGGER.info("添加好友[单个]: " + addFriendSingleNode.toString());
//        }

        /**
         * 查看好友
         */
//        ObjectNode getFriendsNode = getFriends(ownerUserPrimaryKey);
//        if (null != getFriendsNode) {
//            LOGGER.info("查看好友: " + getFriendsNode.toString());
//        }

        /**
         * 解除好友关系
         **/
//        ObjectNode deleteFriendSingleNode = deleteFriendSingle(ownerUserPrimaryKey, friendUserPrimaryKey);
//        if (null != deleteFriendSingleNode) {
//            LOGGER.info("解除好友关系: " + deleteFriendSingleNode.toString());
//        }

        /**
         * 删除IM用户[单个]
         */
//        ObjectNode deleteIMUserByUserPrimaryKeyNode = deleteIMUserByUserPrimaryKey(userPrimaryKey);
//        if (null != deleteIMUserByUserPrimaryKeyNode) {
//            LOGGER.info("删除IM用户[单个]: " + deleteIMUserByUserPrimaryKeyNode.toString());
//        }

        /**
         * 删除IM用户[批量]
         */
//        Long limit = 2l;
//        ObjectNode deleteIMUserByUsernameBatchNode = deleteIMUserByUsernameBatch(limit, null);
//        if (null != deleteIMUserByUsernameBatchNode) {
//            LOGGER.info("删除IM用户[批量]: " + deleteIMUserByUsernameBatchNode.toString());
//        }
    	

    }

    /**
	 * 注册IM用户[单个]
	 * 
	 * 给指定AppKey创建一个新的用户
	 * 
	 * @param dataNode
	 * @return
	 */
	public static ObjectNode createNewIMUserSingle(ObjectNode dataNode) {

		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		objectNode.removeAll();

		// check properties that must be provided
		if (null != dataNode && !dataNode.has("username")) {
			LOGGER.error("Property that named username must be provided .");

			objectNode.put("message",
					"Property that named username must be provided .");

			return objectNode;
		}
		if (null != dataNode && !dataNode.has("password")) {
			LOGGER.error("Property that named password must be provided .");

			objectNode.put("message",
					"Property that named password must be provided .");

			return objectNode;
		}

		try {
			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.USERS_TARGET.resolveTemplate("org_name",
					APPKEY.split("#")[0]).resolveTemplate("app_name",
					APPKEY.split("#")[1]);

			objectNode = JerseyUtils.sendRequest(webTarget, dataNode, credential, HTTPMethod.METHOD_POST, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 注册IM用户[批量]
	 * 
	 * 给指定AppKey创建一批用户
	 * 
	 * @param dataArrayNode
	 * @return
	 */
	public static ObjectNode createNewIMUserBatch(ArrayNode dataArrayNode) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);
			objectNode.put("message", "Bad format of Appkey");
			return objectNode;
		}

		// check properties that must be provided
		if (dataArrayNode.isArray()) {
			for (JsonNode jsonNode : dataArrayNode) {
				if (null != jsonNode && !jsonNode.has("username")) {
					LOGGER.error("Property that named username must be provided .");

					objectNode.put("message", "Property that named username must be provided .");

					return objectNode;
				}
				if (null != jsonNode && !jsonNode.has("password")) {
					LOGGER.error("Property that named password must be provided .");

					objectNode.put("message", "Property that named password must be provided .");

					return objectNode;
				}
			}
		}

		try {

			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.USERS_TARGET.resolveTemplate("org_name",
					APPKEY.split("#")[0]).resolveTemplate("app_name", APPKEY.split("#")[1]);

			objectNode = JerseyUtils.sendRequest(webTarget, dataArrayNode, credential, HTTPMethod.METHOD_POST, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 注册IM用户[批量生成用户然后注册]
	 * 
	 * 给指定AppKey创建一批用户
	 * 
	 * @param usernamePrefix
	 *            生成用户名的前缀
	 * @param perNumber
	 *            批量注册时一次注册的数量
	 * @param totalNumber
	 *            生成用户注册的用户总数
	 * @return
	 */
	public static ObjectNode createNewIMUserBatchGen(String usernamePrefix,
			Long perNumber, Long totalNumber) {
		ObjectNode objectNode = factory.objectNode();

		if (totalNumber == 0 || perNumber == 0) {
			return objectNode;
		}

		System.out.println("你即将注册" + totalNumber + "个用户，如果大于" + perNumber
				+ "了,会分批注册,每次注册" + perNumber + "个");

		ArrayNode genericArrayNode = EasemobIMUsers.genericArrayNode(
				usernamePrefix, totalNumber);
		if (totalNumber <= perNumber) {
			objectNode = EasemobIMUsers.createNewIMUserBatch(genericArrayNode);
		} else {

			for (int i = 0; i < genericArrayNode.size(); i++) {
				ArrayNode tmpArrayNode = factory.arrayNode();
				tmpArrayNode.add(genericArrayNode.get(i));
				// 300 records on one migration
				if ((i + 1) % perNumber == 0) {
					objectNode = EasemobIMUsers
							.createNewIMUserBatch(genericArrayNode);
					tmpArrayNode.removeAll();
					continue;
				}

				// the rest records that less than the times of 300
				if (i > (genericArrayNode.size() / perNumber * perNumber - 1)) {
					objectNode = EasemobIMUsers
							.createNewIMUserBatch(genericArrayNode);
					tmpArrayNode.removeAll();
				}
			}
		}

		return objectNode;
	}

	/**
	 * 获取IM用户
	 * 
	 * @param userPrimaryKey
	 *            用户主键：username或者uuid
	 * @return
	 */
	public static ObjectNode getIMUsersByPrimaryKey(String userPrimaryKey) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		// check properties that must be provided
		if (StringUtils.isEmpty(userPrimaryKey)) {
			LOGGER.error("The primaryKey that will be useed to query must be provided .");

			objectNode
					.put("message",
							"The primaryKey that will be useed to query must be provided .");

			return objectNode;
		}

		try {

			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.USERS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.path(userPrimaryKey);

			objectNode = JerseyUtils.sendRequest(webTarget, null, credential,
					HTTPMethod.METHOD_GET, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 删除IM用户[单个]
	 * 
	 * 删除指定AppKey下IM单个用户
	 *
	 * 
	 * @param userPrimaryKey
	 * @return
	 */
	public static ObjectNode deleteIMUserByUserPrimaryKey(String userPrimaryKey) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		try {
			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.USERS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.path(userPrimaryKey);

			objectNode = JerseyUtils.sendRequest(webTarget, null, credential,
					HTTPMethod.METHOD_DELETE, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 删除IM用户[批量]
	 * 
	 * 批量指定AppKey下删除IM用户
	 * 
	 * @param limit
	 * @param queryStr
	 * @return
	 */
	public static ObjectNode deleteIMUserByUsernameBatch(Long limit,
			String queryStr) {

		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		try {

			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.USERS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.queryParam("ql", queryStr)
					.queryParam("limit", String.valueOf(limit));

			objectNode = JerseyUtils.sendRequest(webTarget, null, credential,
					HTTPMethod.METHOD_DELETE, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 重置IM用户密码 提供管理员token
	 * 
	 * @param userPrimaryKey
	 * @param dataObjectNode
	 * @return
	 */
	public static ObjectNode modifyIMUserPasswordWithAdminToken(
			String userPrimaryKey, ObjectNode dataObjectNode) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		if (StringUtils.isEmpty(userPrimaryKey)) {
			LOGGER.error("Property that named userPrimaryKey must be provided，the value is username or uuid of imuser.");

			objectNode
					.put("message",
							"Property that named userPrimaryKey must be provided，the value is username or uuid of imuser.");

			return objectNode;
		}

		if (null != dataObjectNode && !dataObjectNode.has("newpassword")) {
			LOGGER.error("Property that named newpassword must be provided .");

			objectNode.put("message",
					"Property that named newpassword must be provided .");

			return objectNode;
		}

		try {

			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.USERS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.path(userPrimaryKey).path("password");

			objectNode = JerseyUtils.sendRequest(webTarget, dataObjectNode,
					credential, HTTPMethod.METHOD_PUT, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}
	
	/**
	 * 修改IM用户昵称
	 * 
	 * @param userPrimaryKey
	 * @param dataObjectNode
	 * @return
	 */
	public static ObjectNode modifyIMUserNickName(
			String userPrimaryKey, ObjectNode dataObjectNode) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		if (StringUtils.isEmpty(userPrimaryKey)) {
			LOGGER.error("Property that named userPrimaryKey must be provided，the value is username or uuid of imuser.");

			objectNode
					.put("message",
							"Property that named userPrimaryKey must be provided，the value is username or uuid of imuser.");

			return objectNode;
		}

		if (null != dataObjectNode && !dataObjectNode.has("nickname")) {
			LOGGER.error("Property that named nickname must be provided .");

			objectNode.put("message",
					"Property that named nickname must be provided .");

			return objectNode;
		}

		try {

			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.USERS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.path(userPrimaryKey);

			objectNode = JerseyUtils.sendRequest(webTarget, dataObjectNode,
					credential, HTTPMethod.METHOD_PUT, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}	

	/**
	 * 添加好友[单个]
	 * 
	 * @param ownerUserPrimaryKey
	 * @param friendUserPrimaryKey
	 * 
	 * @return
	 */
	public static ObjectNode addFriendSingle(String ownerUserPrimaryKey,
			String friendUserPrimaryKey) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		if (StringUtils.isEmpty(ownerUserPrimaryKey)) {
			LOGGER.error("Your userPrimaryKey must be provided，the value is username or uuid of imuser.");

			objectNode
					.put("message",
							"Your userPrimaryKey must be provided，the value is username or uuid of imuser.");

			return objectNode;
		}

		if (StringUtils.isEmpty(friendUserPrimaryKey)) {
			LOGGER.error("The userPrimaryKey of friend must be provided，the value is username or uuid of imuser.");

			objectNode
					.put("message",
							"The userPrimaryKey of friend must be provided，the value is username or uuid of imuser.");

			return objectNode;
		}

		try {
			JerseyWebTarget webTarget = null;
			webTarget = EndPoints.USERS_ADDFRIENDS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.resolveTemplate("ownerUserPrimaryKey", ownerUserPrimaryKey)
					.resolveTemplate("friendUserPrimaryKey",
							friendUserPrimaryKey);

			ObjectNode body = factory.objectNode();
			objectNode = JerseyUtils.sendRequest(webTarget, body, credential,
					HTTPMethod.METHOD_POST, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 解除好友关系
	 * 
	 * @param ownerUserPrimaryKey
	 * @param friendUserPrimaryKey
	 * 
	 * @return
	 */
	public static ObjectNode deleteFriendSingle(String ownerUserPrimaryKey,
			String friendUserPrimaryKey) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		if (StringUtils.isEmpty(ownerUserPrimaryKey)) {
			LOGGER.error("Your userPrimaryKey must be provided，the value is username or uuid of imuser.");

			objectNode.put("message",
							"Your userPrimaryKey must be provided，the value is username or uuid of imuser.");

			return objectNode;
		}

		if (StringUtils.isEmpty(friendUserPrimaryKey)) {
			LOGGER.error("The userPrimaryKey of friend must be provided，the value is username or uuid of imuser.");

			objectNode
					.put("message",
							"The userPrimaryKey of friend must be provided，the value is username or uuid of imuser.");

			return objectNode;
		}

		try {

			JerseyWebTarget webTarget = EndPoints.USERS_ADDFRIENDS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.resolveTemplate("ownerUserPrimaryKey", ownerUserPrimaryKey)
					.resolveTemplate("friendUserPrimaryKey",
							friendUserPrimaryKey);

			ObjectNode body = factory.objectNode();
			objectNode = JerseyUtils.sendRequest(webTarget, body, credential, HTTPMethod.METHOD_DELETE, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 查看好友
	 * 
	 * @param ownerUserPrimaryKey
	 * 
	 * @return
	 */
	public static ObjectNode getFriends(String ownerUserPrimaryKey) {
		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}

		if (StringUtils.isEmpty(ownerUserPrimaryKey)) {
			LOGGER.error("Your userPrimaryKey must be provided，the value is username or uuid of imuser.");

			objectNode.put("message",
							"Your userPrimaryKey must be provided，the value is username or uuid of imuser.");

			return objectNode;
		}

		try {
			JerseyWebTarget webTarget = EndPoints.USERS_ADDFRIENDS_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1])
					.resolveTemplate("ownerUserPrimaryKey", ownerUserPrimaryKey)
					.resolveTemplate("friendUserPrimaryKey", "");

			ObjectNode body = factory.objectNode();
			objectNode = JerseyUtils.sendRequest(webTarget, body, credential, HTTPMethod.METHOD_GET, null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * IM用户登录
	 * 
	 * @param ownerUserPrimaryKey
	 * @param password
     *
	 * @return
	 */
	public static ObjectNode imUserLogin(String ownerUserPrimaryKey, String password) {

		ObjectNode objectNode = factory.objectNode();

		// check appKey format
		if (!JerseyUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);

			objectNode.put("message", "Bad format of Appkey");

			return objectNode;
		}
		if (StringUtils.isEmpty(ownerUserPrimaryKey)) {
			LOGGER.error("Your userPrimaryKey must be provided，the value is username or uuid of imuser.");

			objectNode.put("message",
							"Your userPrimaryKey must be provided，the value is username or uuid of imuser.");

			return objectNode;
		}
		if (StringUtils.isEmpty(password)) {
			LOGGER.error("Your password must be provided，the value is username or uuid of imuser.");

			objectNode.put("message",
							"Your password must be provided，the value is username or uuid of imuser.");

			return objectNode;
		}

		try {
			ObjectNode dataNode = factory.objectNode();
			dataNode.put("grant_type", "password");
			dataNode.put("username", ownerUserPrimaryKey);
			dataNode.put("password", password);

			List<NameValuePair> headers = new ArrayList<NameValuePair>();
			headers.add(new BasicNameValuePair("Content-Type", "application/json"));

			objectNode = JerseyUtils.sendRequest(EndPoints.TOKEN_APP_TARGET
					.resolveTemplate("org_name", APPKEY.split("#")[0])
					.resolveTemplate("app_name", APPKEY.split("#")[1]),
					dataNode, null, HTTPMethod.METHOD_POST, headers);

		} catch (Exception e) {
			throw new RuntimeException(	"Some errors ocuured while fetching a token by usename and passowrd .");
		}

		return objectNode;
	}

	/**
	 * 指定前缀和数量生成用户基本数据
	 * 
	 * @param usernamePrefix
	 * @param number
	 * @return
	 */
	private static ArrayNode genericArrayNode(String usernamePrefix, Long number) {
		ArrayNode arrayNode = factory.arrayNode();
		for (int i = 0; i < number; i++) {
			ObjectNode userNode = factory.objectNode();
			userNode.put("username", usernamePrefix + "_" + i);
			userNode.put("password", Constants.DEFAULT_PASSWORD);

			arrayNode.add(userNode);
		}

		return arrayNode;
	}

}
