import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class LabelTeste {
	
	public static void main(String[] args) {

		StringBuilder sb = new StringBuilder();
		sb.append("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC3d+TZyfHUGnxT\nKYA+m+Qeb4jRStvIwwYfcjCsGUMZr83pUf1gFMZAg2m9Q44ICm05hsAdNFP48Vi8\nz3ulldVBb2c2dkmC/WIZ2LM34Wc0I7dAyU7nm9JLC+nlZjawQ8x637fvH+Y5/nwu\nleFjwKd1xslZJHcfTuXbaJXA9hej493Nf6860vTzLW/JqUfJ2mOfIjxaLP/d8EhW\nux3bEY69erK94/XXwhOwQhprUc79bvda2ub4J5Or+7Mg+eQ2b9DXzgmBbnq/pTWL\nMRGnXnDr2ds21moDf1+kKdocoyG1mUAeC4JKuxpZfjKhva1EJgYpNmKEW+UUzucj\niSPlBWTdAgMBAAECggEAaF0rMThO9/iZADV7XiIPgcCwc13B6FIICZtLVJqKMPJw\nj2i0WRN4MvqOC7uB79D+kNq2Lea2XcXsVHsyOt8gkDhu3ewrpPe99jXuvLrbmi02\n8QOSfu2EkmwUgQnD0kC+8KaSWtQqgpLg0R8xNy1AFR8fKjY0DPs0hbrYdQuIJxFh\nT/Sx5auZkbpTpBSEr/heFU6IYBc6NWG9yYqwtWqesGSUlxXReQsixkbvKxcxN6cm\n8cPy1+c8gBf6Ci+COoAxB+iXytGkKhgeqd5TYhVQbRNhYZxVOLleqRHyorg37tlg\n2O2mZ3AF6pEUTvul7RSJzAJjvpefyVVwvc+HPeqkAQKBgQDfH8ZHdh/CnY2TZDOe\nnqJn8F2j5H+I2XpRe7JJpr9ys5OQKjEEXkEWMP0pgZCNODiaCN14Rh+EMvwWILlx\nzVf6pypFoEWvmVwI9hkobHDq5j7GBHF5eRUEmx778oWMgaCB0PVJi/nyFwY0zH7b\nFRFuDhA2F5ABKQfoZvrXd7BrNQKBgQDSgE5gxnmhGmCJyLopBzN95WWuxeLmx560\nanfWnu2MlMH24MHTtRPOkoo7gHRfVWp0AJxGSpLMLl17zhW7S9nKRK4YWNqunMCn\nZ6CcGQcbNexJynjHhiPgnzt13VnUOHelkHK84qNme+Iih5EUtLjN8G91l62TtVrs\nJV8E6x8gCQKBgA72h4qFTm3zuOnYf2m0w7lqsizqhu77fqt2OyebSh18E3pIig77\n7yt4z5F4AeSmj40s6QVG1pg7/xHQ0f7iwXKqwTf4HpQ/GZ/0OQ2v8AaguDb5V07J\nIvKzIGnPAsvAaa4rADgVbgahKqZhzGQOzqNtyAKIo/yf7srUFSBcA3W9AoGAbDeC\nE8Z1aRlCYBNNgSIump6AM+WPzMz7Dg1eJnv167veK6lIfCovUJeNcSGMsiYbYB+g\nN5LG/Sko7ij4ix1njdDZH7MuWW5ErMtdimJjgN2r6BOrHAgeEbBKxUFSmQOenwMv\nuWNaVamCi/Xd5CM0jsr4wh+EINevMo/T1Zd6nOECgYAZrZjrhdVg/GYDOq/yL/k2\nSMWhG7qX04WmuWfxWCWgJ+sSnFpKc5/rWrDgBNkbkJdj+3XI94UuyYozetuCHgi4\nnko8uxnLZP9QfiFnnAwqpPlX9n00TwqUsX0QymsEwgnoPxmhg8iclF4urlKRFF/Z\nIcIK/XWk+eOSBaUbE4OOAA==");
		Client client = Client.create();

		WebResource webResource = client
		   .resource("https://vision.googleapis.com/v1/images:annotate?key=" + sb.toString());

		String input = "{'requests': [ { " +
				  " 'features': [ " +
				  "  { " +
				  " 'type': 'LABEL_DETECTION' " +
				  " } " +
				  " ], " +
				  " 'image': { " +
				  " 'source': { " +
				  " 'gcsImageUri': 'gs://instasearch-141111/upload2/1365268373843655875_2235325390.jpg' " +
				  " } " +
				  " } " +
				  "  } " +
				  " ] " +
				  " }";

		ClientResponse response = webResource.type("application/json")
		   .post(ClientResponse.class, input);
		

		System.out.println(response.getEntity(String.class));
		
	}
}
