library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity test_env is
Port (clk : in STD_LOGIC;
      btn : in STD_LOGIC_VECTOR (4 downto 0);
      sw : in STD_LOGIC_VECTOR (15 downto 0);
      led : out STD_LOGIC_VECTOR (15 downto 0);
      an : out STD_LOGIC_VECTOR (7 downto 0);
      cat : out STD_LOGIC_VECTOR (6 downto 0);
      rst: in std_logic);
end test_env;

architecture Behavioral of test_env is
 
component SSD
Port ( clk : in STD_LOGIC;
       digits : in STD_LOGIC_VECTOR(31 downto 0);
       an : out STD_LOGIC_VECTOR(7 downto 0);
       cat : out STD_LOGIC_VECTOR(6 downto 0));
end component;

component MPG
Port ( enable : out STD_LOGIC;
       btn : in STD_LOGIC;
       clk : in STD_LOGIC);
end component;

component IFetch
Port ( Jump: in std_logic;
       JumpAddress: in std_logic_vector(31 downto 0);
       PCSrc: in std_logic;
       BranchAddress: in std_logic_vector(31 downto 0);
       PCOut: out std_logic_vector(31 downto 0);
       Instruction: out std_logic_vector(31 downto 0);
       Enable: in std_logic;
       Reset: in std_logic;
       clk: in std_logic);
end component;

component ID
Port ( clk : in std_logic;
       instr: in std_logic_vector(25 downto 0);
       en: in std_logic;
       regdst: in std_logic;
       regwr : in std_logic;
       rd1 : out std_logic_vector(31 downto 0);
       rd2 : out std_logic_vector(31 downto 0);
       extop: in std_logic;
       wd: in std_logic_vector(31 downto 0);
       ext_imm: out std_logic_vector(31 downto 0);
       func: out std_logic_vector(5 downto 0);
       sa: out std_logic_vector(4 downto 0));
end component;

component UC
Port ( Instr: in std_logic_vector(5 downto 0);
       RegDst: out std_logic;
       ExtOp: out std_logic;
       ALUSrc: out std_logic;
       Branch: out std_logic;
       Jump: out std_logic;
       ALUOp: out std_logic_vector(1 downto 0);
       MemWrite: out std_logic;
       MemtoReg: out std_logic;
       RegWrt: out std_logic);
end component;

component EX
Port (RD1: in std_logic_vector(31 downto 0);
      RD2: in std_logic_vector(31 downto 0);
      Ext_Imm: in std_logic_vector(31 downto 0);
      sa: in std_logic_vector(4 downto 0);
      func: in std_logic_vector(5 downto 0);
      PC: in std_logic_vector(31 downto 0);
      ALUSrc: in std_logic;
      ALUOp: in std_logic_vector(1 downto 0);
      ALURes: out std_logic_vector(31 downto 0);
      Branch_Address: out std_logic_vector(31 downto 0);
      Zero: out std_logic);
end component;

component MEM
Port (MemWrite: in std_logic;
      ALUResIn: in std_logic_vector(31 downto 0);
      RD2: in std_logic_vector(31 downto 0);
      clk: in std_logic;
      en: in std_logic;
      MemData: out std_logic_vector(31 downto 0);
      ALUResOut: out std_logic_vector(31 downto 0));
end component;

signal btn_Enable: std_logic;
signal btn_Reset: std_logic;
signal jump: std_logic;
signal instruction: std_logic_vector(31 downto 0);
signal pcout_Fetch: std_logic_vector(31 downto 0);
signal jump_Address: std_logic_vector(31 downto 0);
signal branch: std_logic;
signal zero: std_logic;
signal pcSrc:std_logic;
signal branch_address: std_logic_vector(31 downto 0);
signal regdst: std_logic;
signal regwr: std_logic;
signal rd1: std_logic_vector(31 downto 0);
signal rd2: std_logic_vector(31 downto 0);
signal extop: std_logic;
signal aluResOut: std_logic_vector(31 downto 0);
signal aluResIn: std_logic_vector(31 downto 0);
signal memData: std_logic_vector(31 downto 0);
signal memToReg: std_logic;
signal wd: std_logic_vector(31 downto 0);
signal ext_imm: std_logic_vector(31 downto 0);
signal func: std_logic_vector(5 downto 0);
signal sa: std_logic_vector(4 downto 0);
signal alusrc: std_logic;
signal aluop: std_logic_vector(1 downto 0);
signal memwrite: std_logic;
signal iesire: std_logic_vector(31 downto 0);
   
begin

jump_Address <= pcout_Fetch(31 downto 28) & instruction(25 downto 0) & "00";
pcSrc <= branch and zero;
wd <= aluResOut when memToReg = '0' else memData;

led(9 downto 0) <= aluop & regdst & extop & alusrc & branch & jump & memwrite & memToReg & regwr;

process(sw)
begin
    case sw(7 downto 5) is
        when "000" => iesire <= instruction; 
        when "001" => iesire <= pcout_Fetch;
        when "010" => iesire <= rd1;
        when "011" => iesire <= rd2;
        when "100" => iesire <= ext_imm;
        when "101" => iesire <= aluResIn;
        when "110" => iesire <= memData;
        when "111" => iesire <= wd;
        when others => iesire <= X"FFFFFFFF";
    end case;
end process;

MPG_test_Enable: MPG Port map(enable => btn_Enable, btn => btn(0), clk => clk);

MPG_test_Reset: MPG Port map(enable => btn_Reset, btn => btn(1), clk => clk);

IFetch_test: IFetch Port map(Jump => jump, JumpAddress => jump_Address, PCSrc => pcSrc, BranchAddress => branch_address, 
                         PCOut => pcout_Fetch, Instruction => instruction, Enable => btn_Enable, Reset => btn_Reset, clk => clk);

ID_test: ID Port map(clk => clk, instr => instruction(25 downto 0), en => btn_Enable, regdst => regdst, regwr => regwr, rd1 => rd1, 
                     rd2 => rd2, extop => extop, wd => wd, ext_imm => ext_imm, func => func, sa => sa);

UC_test: UC Port map(Instr => instruction(31 downto 26), RegDst => regdst, ExtOp => extop, ALUSrc => alusrc, 
              Branch => branch, Jump => jump, ALUOp => aluop, MemWrite => memwrite, MemtoReg => memToReg, RegWrt => regwr);

EX_test: EX Port map(RD1 => rd1, RD2 => rd2, Ext_Imm => ext_imm, sa => sa, func => func, PC => pcout_Fetch, 
                     ALUSrc => alusrc, ALUOp => aluop, ALURes => aluResIn, Branch_Address => branch_address, Zero => zero);

MEM_test: MEM Port map(MemWrite => memwrite, ALUResIn => aluResIn, RD2 => rd2, clk => clk, en => btn_Enable, 
                       MemData => memData, ALUResOut => aluResOut);

SSD_test: SSD Port map(clk => clk, digits => iesire, an => an, cat => cat);

end Behavioral;
